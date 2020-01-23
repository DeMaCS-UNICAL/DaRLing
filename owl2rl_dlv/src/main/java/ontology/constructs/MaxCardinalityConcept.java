package ontology.constructs;

public class MaxCardinalityConcept extends Concept {

	private int n;
	private Role role;
	private Concept concept;
	
	public MaxCardinalityConcept() {
		super();
		role = new Role();
	}

	public MaxCardinalityConcept(int n, Role role, Concept concept) {
		super();
		this.n = n;
		this.role = role;
		this.concept = concept;
	}

	public int getMaxCardinality() {
		return n;
	}

	public void setMaxCardinality(int n) {
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
		if (obj instanceof MaxCardinalityConcept) {
			MaxCardinalityConcept con = (MaxCardinalityConcept) obj;
			return n == con.getMaxCardinality() && role.equals(con.getRole()) && concept.equals(con.getConcept());
		}
		return false;
	}

	@Override
	public String toString() {
		return "MAX_" + n + "_" + role + "_" + concept;
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
	public MaxCardinalityConcept getStructuralTransformation() {
		MaxCardinalityConcept st = new MaxCardinalityConcept();
		st.setMaxCardinality(this.getMaxCardinality());
		st.setRole(this.getRole());
		st.setConcept(this.getFreshAtomicConcept());
		return st;
	}

}
