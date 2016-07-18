package itv.kata;

import java.math.BigDecimal;

public interface CheckoutService {

	public void beep(Sku sku_) throws BasketException;
	
	public void reset();
	
	public BigDecimal getCurrentTotal();
	
	public Total finish();
}
