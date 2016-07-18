package itv.kata;

import java.util.List;

/**
 * Speaks to offers persistence layer to get and set offer data
 * 
 * Expect multiple offers to be associated with a product
 * 
 * @author sasaunde
 *
 */
public interface OffersService {

	public List<Offer> getOffers(Sku sku_);
	
	public void setOffer(Sku sku_, Offer offer_);
	
	public void removeOffer(Sku sku_, Offer offer_);
	
	public void updateOffer(Sku sku_, Offer offer_);
}
