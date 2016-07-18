package itv.kata;

import java.math.BigDecimal;

/**
 * Speaks to pricing persistence data
 * Expect prices to be stored per product, but separate from the product information
 * Expect join on product ID to price data
 * @author sasaunde
 *
 */
public interface PriceService {

	public Price getPrice(Sku sku_);
			
	public void setPrice(Sku sku_, BigDecimal price_);
}
