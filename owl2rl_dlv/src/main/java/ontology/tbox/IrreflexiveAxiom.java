package ontology.tbox;

import ontology.constructs.Role;

public class IrreflexiveAxiom {
	
	private Role role;
	
	public IrreflexiveAxiom() {
		role = new Role();
	}

	public IrreflexiveAxiom(Role role) {
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
		return "<IRREFLEXIVE>(" + role + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IrreflexiveAxiom) {
			IrreflexiveAxiom ax = (IrreflexiveAxiom) obj;
			return role.equals(ax.getRole());
		}
		return false;
	}

}
