package ontology.constructs;

public class BottomConcept extends Concept{

	public BottomConcept() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BottomConcept)
			return true;
		return false;
	}	
	
	@Override
	public String toString() {
		return "BOTTOM";
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
