package datalog.costructs;

public class Constant extends Term{

	private boolean isInteger;

	public Constant() {
		super();
		isInteger = false;
	}
	
	public Constant(String name) {
		super(name);
		isInteger = false;
	}
	
	public Constant(String name, boolean isInteger) {
		super(name);
		this.isInteger = isInteger;
	}
	
	public boolean isInteger() {
		return isInteger;
	}

	public void setInteger(boolean isInteger) {
		this.isInteger = isInteger;
	}

	@Override
	public String toString() {
		if (!this.isInteger) {
			return "\"" + this.getName() + "\"";
		} else {
			return this.getName();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Constant) {
			Constant con = (Constant) obj;
			return this.getName().equals(con.getName());
		}
		return false;
	}
	
}
