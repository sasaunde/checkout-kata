package itv.kata;

import java.math.BigDecimal;
import java.util.List;

/**
 * Simplified version of an offer that has a fixed discount amount once triggered.
 * @author sasaunde
 *
 */
public class Offer {

	private Sku _applicableSku;
	
	private int _numRequiredToTriggerDiscount;
	
	private BigDecimal _discount;
	
	private String _name;


	public Sku getApplicableSku() {
		return _applicableSku;
	}

	public void setApplicableSku(Sku applicableSku) {
		this._applicableSku = applicableSku;
	}

	
	public int getNumRequiredToTriggerDiscount() {
		return _numRequiredToTriggerDiscount;
	}

	public void setNumRequiredToTriggerDiscount(int _numRequiredToTriggerDiscount) {
		this._numRequiredToTriggerDiscount = _numRequiredToTriggerDiscount;
	}

	public BigDecimal getDiscount() {
		return _discount;
	}

	public void setDiscount(BigDecimal _discount) {
		this._discount = _discount;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
	
	public boolean isApplicableTo(List<Sku> basket_) {
		long numApplicable = basket_.stream().filter(x -> x.equals(_applicableSku)).count();
		
		if(numApplicable >= _numRequiredToTriggerDiscount) {
			return true;
		}
		return false;
	}
	
	
}
