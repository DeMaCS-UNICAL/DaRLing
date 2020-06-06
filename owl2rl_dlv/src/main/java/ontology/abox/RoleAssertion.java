package ontology.abox;

import ontology.constructs.Individual;
import ontology.constructs.Role;

public class RoleAssertion {
	
	private Role role;
	private Individual individual1;
	private Individual individual2;
	private boolean isIndividual2Integer;
	
	public RoleAssertion() { }

	public RoleAssertion(Role role, Individual individual1, Individual individual2) {
		this.role = role;
		this.individual1 = individual1;
		this.individual2 = individual2;
		isIndividual2Integer = false;
	}
	
	public boolean isIndividual2Integer() {
		return isIndividual2Integer;
	}
	
	public void setIndividual2Integer (boolean isIndividual2Integer) {
		this.isIndividual2Integer = isIndividual2Integer;
	}
	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Individual getIndividual1() {
		return individual1;
	}

	public void setIndividual1(Individual individual1) {
		this.individual1 = individual1;
	}

	public Individual getIndividual2() {
		return individual2;
	}

	public void setIndividual2(Individual individual2) {
		this.individual2 = individual2;
	}

	@Override
	public String toString() {
		return role + "(" + individual1 + "," + individual2 + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoleAssertion) {
			RoleAssertion ra = (RoleAssertion) obj;
			return role.equals(ra.getRole()) && individual1.equals(ra.getIndividual1()) && individual2.equals(ra.getIndividual2());
		}
		return false;
	}

}
