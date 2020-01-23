package ontology.constructs;

public class MinCardinalityConcept extends Concept {
	
	private int n;
	private Role role;
	private Concept concept;

	public MinCardinalityConcept() {
		super();
		role = new Role();
	}

	public MinCardinalityConcept(int n, Role role, Concept concept) {
		super();
		this.n = n;
		this.role = role;
		this.concept = concept;
	}

	public int getMinCardinality() {
		return n;
	}

	public void setMinCardinality(int n) {
		this.n = n;
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
		if (obj instanceof MinCardinalityConcept) {
			MinCardinalityConcept con = (MinCardinalityConcept) obj;
			return n == con.getMinCardinality() && role.equals(con.getRole()) && concept.equals(con.getConcept());
		}
		return false;
	}

	@Override
	public String toString() {
		return "MIN_" + n + "_" + role + "_" + concept;
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
	public MinCardinalityConcept getStructuralTransformation() {
		MinCardinalityConcept st = new MinCardinalityConcept();
		st.setMinCardinality(this.getMinCardinality());
		st.setRole(this.getRole());
		st.setConcept(this.getFreshAtomicConcept());
		return st;
	}

}
