package ontology.tbox;

public class UnmanagedAxiom {
	
	private String owlAxiom;
	
	public UnmanagedAxiom(String owlAxiom) {
		this.owlAxiom = owlAxiom;
	}

	public String getOwlAxiom() {
		return owlAxiom;
	}

	public void setOwlAxiom(String owlAxiom) {
		this.owlAxiom = owlAxiom;
	}
	
	@Override
	public String toString() {
		return owlAxiom;
	}

}
