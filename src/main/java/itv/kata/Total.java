package itv.kata;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class Total {
	private BigDecimal _totalBeforeOffers;
	
	private BigDecimal _offers;
	
	private List<String> _offerNames;
	
	public Total(BigDecimal total_, BigDecimal offers_, List<String> offerNames_) {
		this._totalBeforeOffers = total_;
		this._offers = offers_;
		this._offerNames = offerNames_;		
	}
	
	public BigDecimal getTotalBeforeOffers() {
		return _totalBeforeOffers;
	}
	
	public BigDecimal getOffers() {
		return _offers;
	}
	
	public BigDecimal getTotalWithOffersApplied() {
		return _totalBeforeOffers.subtract(_offers).round(MathContext.DECIMAL32);
	}
	
	public List<String> getOfferNames() {
		return _offerNames;
	}
	
}
