package datalog.costructs;

public class Head {
	
	private Literal headAtom;

	public Head() {
		headAtom = new Literal();
	}
	
	public Head(Literal headAtom) {
		this.headAtom = headAtom;
	}

	public Literal getHeadAtom() {
		return headAtom;
	}

	public void setHeadAtom(Literal headAtom) {
		this.headAtom = headAtom;
	}
	
	@Override
	public String toString() {
		return headAtom.toString();
	}

}
