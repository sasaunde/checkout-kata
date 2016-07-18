package itv.kata;

public class Sku {
	
	private Double _weight;
	
	private String _name;
	
	private String _id;
	
	public Sku(String id_, String name_, Double weight_) {
		this._id = id_;
		this._weight = weight_;
		this._name = name_;
	}
	
	public Double getWeight() {
		return _weight;
	}

	public String getName() {
		return _name;
	}

	public String getId() {
		return _id;
	}
	
}
