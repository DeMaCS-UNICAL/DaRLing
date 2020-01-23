package datalog.costructs;

import java.util.ArrayList;

public class Literal {
	
	private final static String KEYWORD = "not";
	
	private String predicateName;
	private int arity;
	private ArrayList<Term> arguments;
	private boolean negative;
	
	public Literal() {
		predicateName = "";
		arguments = new ArrayList<Term>();
		negative = false;
	}
	
	public Literal(String predicateName, int arity) {
		this.predicateName = predicateName;
		this.arity = arity;
		arguments = new ArrayList<Term>();
		negative = false;
	}
	
	public Literal(String predicateName, int arity, ArrayList<Term> arguments) {
		this.predicateName = predicateName;
		this.arity = arity;
		this.arguments = arguments;
		negative = false;
	}
	
	public Literal(String predicateName, int arity, ArrayList<Term> arguments, boolean negative) {
		this.predicateName = predicateName;
		this.arity = arity;
		this.arguments = arguments;
		this.negative = negative;
	}

	public String getPredicateName() {
		return predicateName;
	}

	public void setPredicateName(String predicateName) {
		this.predicateName = predicateName;
	}

	public int getArity() {
		return arity;
	}

	public void setArity(int arity) {
		this.arity = arity;
	}

	public ArrayList<Term> getArguments() {
		return arguments;
	}
	
	public void setArguments(ArrayList<Term> arguments) {
		this.arguments = arguments;
	}

	public boolean isNegative() {
		return negative;
	}

	public void setNegative(boolean negative) {
		this.negative = negative;
	}

	@Override
	public String toString() {
		if (predicateName.equals("")) {
			return "";
		}
		String atom = predicateName + "(";
		for (int i = 0; i < arity; i++) {
			atom += arguments.get(i);
			if (i < arity-1)
				atom += ",";
		}
		atom += ")";
		return negative ? KEYWORD + " " + atom : atom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Literal) {
			Literal literal = (Literal) obj;
			return predicateName.equals(literal.getPredicateName()) && arity == literal.getArity() && arguments.equals(literal.getArguments()) && negative == literal.isNegative();	
		}
		return false;
	}
	

}
