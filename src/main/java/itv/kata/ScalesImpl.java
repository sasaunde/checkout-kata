package itv.kata;

public class ScalesImpl implements Scales {

	private Double _weight = 0.0;
	
	
	public synchronized void addWeight(Double weight_) {
		_weight += weight_;
	}
	
	public synchronized void removeWeight(Double weight_) {
		_weight -= weight_;
	}
	
	public Double geWeight( ) {
		return _weight;
	}
	
	
	public boolean check(Double basketWeight_) throws BasketException {
		if(!acceptableVariation(basketWeight_)) {
			throw new BasketException("Weight does not match basket");
		}
		return true;
	}
	
	protected boolean acceptableVariation(Double basketWeight_) {
		// Simplify and allow "1" leeway
		return basketWeight_ <= _weight + 1 && basketWeight_ >= _weight - 1;
	}

	@Override
	public void reset() {
		synchronized(_weight) {
			_weight = 0.0;
		}
		
	}
}

