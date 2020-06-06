package query.constructs;

public abstract class Term {

	protected String name;
	
	public Term() { }
	
	public abstract String toString(); 
	public abstract boolean equals(Object obj);
	
}
