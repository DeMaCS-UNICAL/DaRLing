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


package ontology.constructs;

public class ExistentialConcept extends Concept{

	private Role role;
	private Concept concept;
	
	public ExistentialConcept() {
		super();
		role = new Role();
	}
	
	public ExistentialConcept(Role role, Concept concept) {
		super();
		this.role = role;
		this.concept = concept;
	}
	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExistentialConcept) {
			ExistentialConcept exCon = (ExistentialConcept) obj;
			return role.equals(exCon.getRole()) && concept.equals(exCon.getConcept());
		}
		return false;
	}

	@Override
	public String toString() {
		return "EXISTS_" + role + "_" + concept;
	}

	@Override
	public boolean isAtomic() {
		return false;
	}

	@Override
	public AtomicConcept getFreshAtomicConcept() {
		String name = "fresh_" + this.toString();
		return new AtomicConcept(name);
	}

	@Override
	public ExistentialConcept getStructuralTransformation() {
		ExistentialConcept st = new ExistentialConcept();
		st.setRole(this.getRole());
		st.setConcept(this.getFreshAtomicConcept());
		return st;
	}

}
