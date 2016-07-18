package itv.kata;


import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class CheckoutServiceTest extends TestCase {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private PriceService priceServiceMock =  mock(PriceService.class);
	
	private OffersService offerServiceMock = mock(OffersService.class);
	
	private Scales scalesImpl = new ScalesImpl();
	
	private Scales mockScales = mock(Scales.class);
	
	
	/**
	 * Convenicence method to create mock data object
	 * @param sku_
	 * @return
	 */
	private Offer getOffer(Sku sku_) {

		Offer offer = new Offer();
		offer.setName("10p off cabbages");
		offer.setApplicableSku(sku_);
		offer.setDiscount(new BigDecimal(0.10));
		offer.setNumRequiredToTriggerDiscount(1);
		
		return offer;
	}
	
	@Test
	public void testOffer() {
		
		Sku cabbage = new Sku("test", "Cabbage", 10.0);
		
		List<Sku> cabbages = new ArrayList<Sku>();
		cabbages.add(cabbage);
		
		Offer offer = getOffer(cabbage);
		
		assertTrue(offer.isApplicableTo(cabbages));
	}
	
	@Test
	public void testComplexOffer() {
		Sku a = new Sku("A", "a", 10.0);
		Sku b = new Sku("B", "b", 15.0);
		
		Offer offer1 = new Offer();
		offer1.setApplicableSku(a);
		offer1.setDiscount(new BigDecimal(20));
		offer1.setName("Three for 1.30");
		offer1.setNumRequiredToTriggerDiscount(3);
		

		Offer offer2 = new Offer();
		offer2.setApplicableSku(b);
		offer2.setDiscount(new BigDecimal(15));
		offer2.setName("Two for 45");
		offer2.setNumRequiredToTriggerDiscount(2);
		
		List<Sku> basket = new ArrayList<Sku>();
		basket.add(a);
		basket.add(a);
		basket.add(a);
		basket.add(b);
		
		assertTrue(offer1.isApplicableTo(basket));
		assertFalse(offer2.isApplicableTo(basket));
	}
	
	
	@Test
	public void testBasketPrice() {
		Sku cabbage = new Sku("test", "Cabbage", 10.0);
		Price cabbagePrice = new Price(cabbage, new BigDecimal(0.39));
		
		when(priceServiceMock.getPrice(cabbage)).thenReturn(cabbagePrice);
		
		Offer offer = getOffer(cabbage);
		
		when(offerServiceMock.getOffers(cabbage)).thenReturn(Arrays.asList(offer));
		
		CheckoutService checkoutService = new CheckoutServiceImpl(scalesImpl, offerServiceMock, priceServiceMock);
		
		try {
			checkoutService.beep(cabbage);
		} catch (BasketException e) {
			// TODO Auto-generated catch block
			fail("Exception thrown : " + e.getMessage());
		}
		
		Total total = checkoutService.finish();
		
		assertEquals(1, total.getOfferNames().size());
		assertEquals(new BigDecimal(0.10), total.getOffers());
		assertEquals(new BigDecimal(0.39), total.getTotalBeforeOffers());
		assertEquals(new BigDecimal(0.29).round(MathContext.DECIMAL32), total.getTotalWithOffersApplied());
		
		verify(offerServiceMock, times(1)).getOffers(cabbage);
		
	}
	
	
	
	@Test
	public void testCheckCalls() throws BasketException {
		
		
		when(mockScales.check(any())).thenThrow(new BasketException("Test basket exception"));
		
		CheckoutService checkoutService = new CheckoutServiceImpl(mockScales, offerServiceMock, priceServiceMock);

		try {
			checkoutService.beep(new Sku("test", "cabbage", 10.0));
		} catch (BasketException expected) {}
		
		
		try {
			verify(mockScales, times(11)).check(any());
		} catch (BasketException ignored) {
			
		}
		
	}
	
	@Test
	public void testCheckBasket() {
		Sku cabbage = new Sku("test", "Cabbage", 10.0);
		
		CheckoutService checkoutService = new CheckoutServiceImpl(scalesImpl, offerServiceMock, priceServiceMock);
		
		
		try {
			checkoutService.beep(cabbage);
			
		} catch (BasketException b) {
			fail("No exception should have been thrown");
		}
		
		// Add an illegal weight to the scales
		scalesImpl.addWeight(1.5);
		
		try {
			checkoutService.beep(cabbage);
		
			fail("No exception thrown");
		} catch (BasketException e) {
			assertEquals("Weight does not match basket", e.getMessage());
		}
		
		// Reset the scales
		checkoutService.reset();
		

		
	}

	
	@Test
	public void testItvData() {
		// Simulate data provided by ITV kata
		Sku a = new Sku("A", "a", 10.0);
		Sku b = new Sku("B", "b", 15.0);
		Sku c = new Sku("C", "c", 6.0);
		Sku d = new Sku("D", "d", 7.0);
		
		Offer offer1 = new Offer();
		offer1.setApplicableSku(a);
		offer1.setDiscount(new BigDecimal(20));
		offer1.setName("Three for 1.30");
		offer1.setNumRequiredToTriggerDiscount(3);
		

		Offer offer2 = new Offer();
		offer2.setApplicableSku(b);
		offer2.setDiscount(new BigDecimal(15));
		offer2.setName("Two for 45");
		offer2.setNumRequiredToTriggerDiscount(2);
		
		when(priceServiceMock.getPrice(a)).thenReturn(new Price(a, new BigDecimal(50)));

		when(priceServiceMock.getPrice(b)).thenReturn(new Price(b, new BigDecimal(30)));

		when(priceServiceMock.getPrice(c)).thenReturn(new Price(c, new BigDecimal(20)));

		when(priceServiceMock.getPrice(d)).thenReturn(new Price(d, new BigDecimal(15)));
		
		when(offerServiceMock.getOffers(a)).thenReturn(Arrays.asList(offer1));
		
		when(offerServiceMock.getOffers(b)).thenReturn(Arrays.asList(offer2));
		
		
		CheckoutService checkoutService = new CheckoutServiceImpl(scalesImpl, offerServiceMock, priceServiceMock);
		
		try {
			checkoutService.beep(b);
			checkoutService.beep(a);
			checkoutService.beep(b);
			
			Total total = checkoutService.finish();
			
			assertEquals(1, total.getOfferNames().size());
			assertEquals(new BigDecimal(15), total.getOffers());
			assertEquals(new BigDecimal(110), total.getTotalBeforeOffers());
			
			assertEquals(new BigDecimal(95).round(MathContext.DECIMAL32), total.getTotalWithOffersApplied());
			
		} catch (BasketException e) {
			fail(e.getMessage());
		}
		
		
	}
}
