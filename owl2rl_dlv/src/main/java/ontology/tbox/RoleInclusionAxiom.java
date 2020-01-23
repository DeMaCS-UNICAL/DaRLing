package ontology.tbox;

import ontology.constructs.Role;

public class RoleInclusionAxiom {
	
	private Role subRole;
	private Role superRole;
	
	public RoleInclusionAxiom() { 
		subRole = new Role();
		superRole = new Role();
	}
	
	public RoleInclusionAxiom(Role subRole, Role superRole) {
		this.subRole = subRole;
		this.superRole = superRole;
	}

	public Role getSubRole() {
		return subRole;
	}

	public void setSubRole(Role subRole) {
		this.subRole = subRole;
	}

	public Role getSuperRole() {
		return superRole;
	}

	public void setSuperRole(Role superRole) {
		this.superRole = superRole;
	}

	@Override
	public String toString() {
		return subRole + " <SUB_ROLE_OF> " + superRole;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoleInclusionAxiom) {
			RoleInclusionAxiom ax = (RoleInclusionAxiom) obj;
			return subRole.equals(ax.getSubRole()) && superRole.equals(ax.getSuperRole());
		}
		return false;
	}

}
