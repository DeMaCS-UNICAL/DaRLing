package datalog.costructs;

public class NotEqualToLiteral {
	
	private final static String KEYWORD = " != "; 
	
	private Variable leftHandSide;
	private Term rightHandSide;
	
	public NotEqualToLiteral() {}
	
	public NotEqualToLiteral(Variable leftHandSide, Term rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public Variable getLeftHandSide() {
		return leftHandSide;
	}
	public void setLeftHandSide(Variable leftHandSide) {
		this.leftHandSide = leftHandSide;
	}
	public Term getRightHandSide() {
		return rightHandSide;
	}
	public void setRightHandSide(Term rightHandSide) {
		this.rightHandSide = rightHandSide;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotEqualToLiteral) {
			NotEqualToLiteral ineq = (NotEqualToLiteral) obj;
			return leftHandSide.equals(ineq.getLeftHandSide()) && rightHandSide.equals(ineq.getRightHandSide());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return leftHandSide.toString() + KEYWORD + rightHandSide.toString();
	}

}
