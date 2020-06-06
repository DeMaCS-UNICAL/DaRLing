package ontology.abox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ABox {
	
	private Set<ConceptAssertion> conceptAssertions;
	private Set<RoleAssertion> roleAssertions;
	
	public ABox() { 
		conceptAssertions = new HashSet<ConceptAssertion>();
		roleAssertions = new HashSet<RoleAssertion>();
	}

	public ABox(Set<ConceptAssertion> conceptAssertions, Set<RoleAssertion> roleAssertions) {
		this.conceptAssertions = conceptAssertions;
		this.roleAssertions = roleAssertions;
	}

	public Set<ConceptAssertion> getConceptAssertions() {
		return conceptAssertions;
	}

	public void setConceptAssertions(Set<ConceptAssertion> conceptAssertions) {
		this.conceptAssertions = conceptAssertions;
	}

	public Set<RoleAssertion> getRoleAssertions() {
		return roleAssertions;
	}

	public void setRoleAssertions(Set<RoleAssertion> roleAssertions) {
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
