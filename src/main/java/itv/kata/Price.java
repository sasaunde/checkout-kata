package itv.kata;

import java.math.BigDecimal;

public class Price {
	private Sku _sku;
	
	private BigDecimal _price;
	
	
	public Price(Sku sku_, BigDecimal price_) {
		this._sku = sku_;
		this._price = price_;
	}
	
	public BigDecimal getPrice() {
		return _price;
	}
	

}
