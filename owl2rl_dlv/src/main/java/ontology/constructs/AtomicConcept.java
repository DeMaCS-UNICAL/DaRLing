package ontology.constructs;

public class AtomicConcept extends Concept{
	
	private String name;
	
	public AtomicConcept() {
		super();
		name = "";
	}

	public AtomicConcept(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AtomicConcept) {
			AtomicConcept concept = (AtomicConcept) obj;
			return this.name.equals(concept.getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
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
