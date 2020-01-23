package ontology.tbox;

import ontology.constructs.Role;

public class AsymmetricAxiom {
	
	private Role role;
	
	public AsymmetricAxiom() { 
		role = new Role();
	}

	public AsymmetricAxiom(Role role) {
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
		return "<ASYMMETRIC>(" + role + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AsymmetricAxiom) {
			AsymmetricAxiom ax = (AsymmetricAxiom) obj;
			return role.equals(ax.getRole());
		}
		return false;
	}

}
