package ontology.tbox;

import ontology.constructs.Role;

public class ReflexiveAxiom {
	
	private Role role;
	
	public ReflexiveAxiom() { 
		role = new Role();
	}

	public ReflexiveAxiom(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "<REFLEXIVE>(" + role + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReflexiveAxiom) {
			ReflexiveAxiom ax = (ReflexiveAxiom) obj;
			return role.equals(ax.getRole());
		}
		return false;
	}

}
