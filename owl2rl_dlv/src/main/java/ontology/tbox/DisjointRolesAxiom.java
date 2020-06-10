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
