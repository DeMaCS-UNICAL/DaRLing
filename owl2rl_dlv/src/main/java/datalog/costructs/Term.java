package datalog.costructs;

public abstract class Term {
	
	private String name;
	
	public Term() { }
	
	public Term(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String toString();

}
