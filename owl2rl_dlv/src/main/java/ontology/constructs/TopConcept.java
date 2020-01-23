package ontology.constructs;

public class TopConcept extends Concept{
	
	public TopConcept() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TopConcept)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "TOP";
	}

	@Override
	public boolean isAtomic() {
		return true;
	}

	@Override
	public Concept getFreshAtomicConcept() {
		return this;
	}

	@Override
	public Concept getStructuralTransformation() {
		return this;
	}

}
