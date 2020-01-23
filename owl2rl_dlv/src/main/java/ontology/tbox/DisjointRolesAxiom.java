package ontology.tbox;

import ontology.constructs.Role;

public class DisjointRolesAxiom {
	
	private Role role1;
	private Role role2;
	
	public DisjointRolesAxiom() {
		role1 = new Role();
		role2 = new Role();
	}

	public DisjointRolesAxiom(Role role1, Role role2) {
		this.role1 = role1;
		this.role2 = role2;
	}

	public Role getRole1() {
		return role1;
	}

	public void setRole1(Role role1) {
		this.role1 = role1;
	}

	public Role getRole2() {
		return role2;
	}

	public void setRole2(Role role2) {
		this.role2 = role2;
	}

	@Override
	public String toString() {
		return "DISJOINT_ROLES(" + role1 + ", " + role2 + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DisjointRolesAxiom) {
			DisjointRolesAxiom ax = (DisjointRolesAxiom) obj;
			return role1.equals(ax.getRole1()) && role2.equals(getRole2());
		}
		return false;
	}
	
	

}
