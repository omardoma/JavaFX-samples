
public abstract class Vehicle {
	private String make;
	private String model;
	private String colour;
	private String regNum;
	
	public Vehicle(String regNum) {
		this.regNum = regNum;
	}
	
	protected String getMake() {
		return make;
	}
	
	protected void setMake(String make) {
		this.make = make;
	}
	
	protected String getModel() {
		return model;
	}
	
	protected void setModel(String model) {
		this.model = model;
	}
	
	protected String getColour() {
		return colour;
	}
	
	protected void setColour(String colour) {
		this.colour = colour;
	}
	
	protected String getRegNum() {
		return regNum;
	}
}
