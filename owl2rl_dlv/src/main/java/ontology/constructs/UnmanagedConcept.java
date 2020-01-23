package ontology.constructs;

public class UnmanagedConcept extends Concept {

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UnmanagedConcept)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "unmanagedConcept";
	}

	@Override
	public boolean isAtomic() {
		return false;
	}

	@Override
	public Concept getFreshAtomicConcept() {
		return null;
	}

	@Override
	public Concept getStructuralTransformation() {
		return null;
	}

}
