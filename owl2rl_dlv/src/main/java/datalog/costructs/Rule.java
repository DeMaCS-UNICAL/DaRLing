package datalog.costructs;

public class Rule {
	
	private final static String KEYWORD = ":-";
	private Head head;
	private Body body;

	public Rule() {
		head = new Head();
		body = new Body();
	}
	
	public Rule(Head head, Body body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		String headAtom = head.toString();
		String bodyLiterals = body.toString();
		return (bodyLiterals.equals("")) ? headAtom + "." : headAtom + " " + KEYWORD + " " + bodyLiterals; 
	}

}
