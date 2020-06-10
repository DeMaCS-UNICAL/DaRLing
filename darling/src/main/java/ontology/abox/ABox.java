/*******************************************************************************
 * Copyright 2020 Alessio Fiorentino and Marco Manna
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/


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
