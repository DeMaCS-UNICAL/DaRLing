package ontology.tbox;

import ontology.constructs.Role;

public class TransitivityAxiom {
	
	private Role role;
	
	public TransitivityAxiom() { 
		role = new Role();
	}

	public TransitivityAxiom(Role role) {
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
		return "<TRANS>(" + role + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TransitivityAxiom) {
			TransitivityAxiom ax = (TransitivityAxiom) obj;
			return role.equals(ax.getRole());
		}
		return false;
	}
	
	

}
