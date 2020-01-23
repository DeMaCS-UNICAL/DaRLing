package ontology.constructs;

public class UniversalConcept extends Concept {

	private Role role;
	private Concept concept;
	
	public UniversalConcept() {
		super();
		role = new Role();
	}
	
	public UniversalConcept(Role role, Concept concept) {
		super();
		this.role = role;
		this.concept = concept;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UniversalConcept) {
			UniversalConcept unCon = (UniversalConcept) obj;
			return role.equals(unCon.getRole()) && concept.equals(unCon.getConcept());
		}
		return false;
	}

	@Override
	public String toString() {
		return "FOREACH_" + role + "_" + concept;
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
	public UniversalConcept getStructuralTransformation() {
		UniversalConcept st = new UniversalConcept();
		st.setRole(this.getRole());
		st.setConcept(this.getFreshAtomicConcept());
		return st;
	}

}
