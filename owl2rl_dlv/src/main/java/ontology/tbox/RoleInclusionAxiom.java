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
 ******************************************************************************/
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
