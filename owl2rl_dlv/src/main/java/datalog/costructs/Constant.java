package datalog.costructs;

public class Constant extends Term{

	public Constant() {
		super();
	}
	
	public Constant(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "\"" + this.getName() + "\"";
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
