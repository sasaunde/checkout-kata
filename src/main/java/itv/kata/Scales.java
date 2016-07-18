package itv.kata;

/**
 * Represent a scales service, so weight can be added and removed. Checkout can 
 * call a check to compare expected product weight with actual scales weight.
 * 
 * Some leeway allowed.
 * @author sasaunde
 *
 */
public interface Scales {

	public void addWeight(Double weight_);
	
	public void removeWeight(Double weight_);
	
	public void reset();
	
	public boolean check(Double basketWeight_) throws BasketException;
}

class BasketException extends Exception {
	public BasketException(String message_) {
		super(message_);
	}
}