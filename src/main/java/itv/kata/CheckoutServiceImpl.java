package itv.kata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CheckoutServiceImpl implements CheckoutService {

	private Scales _scales;
	
	private List<Sku> _basket = new ArrayList<Sku>();
	
	private OffersService _offersService;
	
	
	private PriceService _priceService;
	
	private int _waitTimeout = 10; // how long you give the customer to put the product in the basket before you check
	
	public CheckoutServiceImpl(Scales scales_, OffersService offersService_, PriceService _priceService) {
		this._offersService = offersService_;
		this._scales = scales_;
		this._priceService = _priceService;
	}

	/**
	 * Method to be called when a new item is scanned at the checkout
	 */
	public void beep(Sku sku_) throws BasketException {
		// Imagine this is triggered by a barcode beep
		_basket.add(sku_);
		_scales.addWeight(sku_.getWeight());
		
		double basketWeight = _basket.stream().mapToDouble(Sku::getWeight).sum();
		
		int timeout = _waitTimeout;
		while(timeout > 0) {
			try {
				_scales.check(basketWeight);
				break;
			} catch (BasketException e) {
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e1) {
					
				}
				timeout--;
			}
		}
		// And once more to trigger exception outside of loop
		_scales.check(basketWeight);
	}

	public void reset() {
		_scales.reset();
		_basket = new ArrayList<Sku>();
	}

	public BigDecimal getCurrentTotal() {
		return _basket.stream().map(x -> _priceService.getPrice(x)).map(Price::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * End your scanning and get your totals
	 */
	public Total finish() {
		List<String> offerNames = new ArrayList<String>();
		
		Stream<Offer> offers =  _basket.stream().map(x -> _offersService.getOffers(x)).flatMap(List::stream).distinct().filter(y -> y.isApplicableTo(_basket) && offerNames.add(y.getName()));
		
		BigDecimal total = getCurrentTotal();
		BigDecimal offerVal = offers.map(Offer::getDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
		// No going back!
		reset();
		
		return new Total(total, 
						 offerVal, 
						 offerNames);
	}
	
	
	
}

