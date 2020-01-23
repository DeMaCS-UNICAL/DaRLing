package datalog.costructs;

public class Variable extends Term {
	
	public Variable() {
		super();
	}

	public Variable(String name) {
		super(name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			Variable var = (Variable) obj;
			return this.getName().equals(var.getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
