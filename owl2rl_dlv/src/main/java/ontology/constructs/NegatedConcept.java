package ontology.constructs;


public class NegatedConcept extends Concept{
	
	private Concept concept;
	
	public NegatedConcept() {
		super();
	}
	
	public NegatedConcept(Concept concept) {
		this.concept = concept;
	}
	
	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof NegatedConcept) {
			NegatedConcept negConcept = (NegatedConcept) obj;
			return this.concept.equals(negConcept.getConcept());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "NEG_" + concept;
	}

	@Override
	public boolean isAtomic() {
		return false;
	}

	@Override
	public AtomicConcept getFreshAtomicConcept() {
		String name = "fresh_" + this.toString(); 
		return new AtomicConcept(name);
	}

	@Override
	public NegatedConcept getStructuralTransformation() {
		NegatedConcept st = new NegatedConcept(this.getFreshAtomicConcept());
		return st;
	}

}
