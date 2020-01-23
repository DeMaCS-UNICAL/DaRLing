package ontology.abox;

import java.util.List;

public class ABox {
	
	private List<ConceptAssertion> conceptAssertions;
	private List<RoleAssertion> roleAssertions;
	
	public ABox() { }

	public ABox(List<ConceptAssertion> conceptAssertions, List<RoleAssertion> roleAssertions) {
		this.conceptAssertions = conceptAssertions;
		this.roleAssertions = roleAssertions;
	}

	public List<ConceptAssertion> getConceptAssertions() {
		return conceptAssertions;
	}

	public void setConceptAssertions(List<ConceptAssertion> conceptAssertions) {
		this.conceptAssertions = conceptAssertions;
	}

	public List<RoleAssertion> getRoleAssertions() {
		return roleAssertions;
	}

	public void setRoleAssertions(List<RoleAssertion> roleAssertions) {
		this.roleAssertions = roleAssertions;
	}

	@Override
	public String toString() {
		String s = "Concept Assertions: \n";
		for (ConceptAssertion c : conceptAssertions) {
			s += c + "\n";
		}
		s += "Role Assertions: \n";
		for (RoleAssertion r : roleAssertions) {
			s += r + "\n";
		}
		return s;
	}
	
	

}
