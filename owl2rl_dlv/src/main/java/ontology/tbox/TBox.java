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

import java.util.HashSet;
import java.util.Set;

public class TBox {
	
	private Set<ConceptInclusionAxiom> conceptInclusions;
	private Set<RoleInclusionAxiom> roleInclusions;
	private Set<TransitivityAxiom> transitivityAxioms;
	private Set<DisjointRolesAxiom> disjointRolesAxioms;
	private Set<AsymmetricAxiom> asymmetricAxioms;
	private Set<ReflexiveAxiom> reflexiveAxioms;
	private Set<IrreflexiveAxiom> irreflexiveAxioms;
	
	public TBox() {
		conceptInclusions = new HashSet<ConceptInclusionAxiom>();
		roleInclusions = new HashSet<RoleInclusionAxiom>();
		transitivityAxioms = new HashSet<TransitivityAxiom>();
		disjointRolesAxioms = new HashSet<DisjointRolesAxiom>();
		asymmetricAxioms = new HashSet<AsymmetricAxiom>();
		reflexiveAxioms = new HashSet<ReflexiveAxiom>();
		irreflexiveAxioms = new HashSet<IrreflexiveAxiom>();
	}
	
	public TBox(Set<ConceptInclusionAxiom> conceptInclusions, Set<RoleInclusionAxiom> roleInclusions,
			Set<TransitivityAxiom> transitivityAxioms, Set<DisjointRolesAxiom> disjointRolesAxioms,
			Set<AsymmetricAxiom> asymmetricAxioms, Set<ReflexiveAxiom> reflexiveAxioms,
			Set<IrreflexiveAxiom> irreflexiveAxioms) {
		this.conceptInclusions = conceptInclusions;
		this.roleInclusions = roleInclusions;
		this.transitivityAxioms = transitivityAxioms;
		this.disjointRolesAxioms = disjointRolesAxioms;
		this.asymmetricAxioms = asymmetricAxioms;
		this.reflexiveAxioms = reflexiveAxioms;
		this.irreflexiveAxioms = irreflexiveAxioms;
	}

	public Set<ConceptInclusionAxiom> getConceptInclusions() {
		return conceptInclusions;
	}

	public void setConceptInclusions(Set<ConceptInclusionAxiom> conceptInclusions) {
		this.conceptInclusions = conceptInclusions;
	}

	public Set<RoleInclusionAxiom> getRoleInclusions() {
		return roleInclusions;
	}

	public void setRoleInclusions(Set<RoleInclusionAxiom> roleInclusions) {
		this.roleInclusions = roleInclusions;
	}

	public Set<TransitivityAxiom> getTransitivityAxioms() {
		return transitivityAxioms;
	}

	public void setTransitivityAxioms(Set<TransitivityAxiom> transitivityAxioms) {
		this.transitivityAxioms = transitivityAxioms;
	}

	public Set<DisjointRolesAxiom> getDisjointRolesAxioms() {
		return disjointRolesAxioms;
	}

	public void setDisjointRolesAxioms(Set<DisjointRolesAxiom> disjointRolesAxioms) {
		this.disjointRolesAxioms = disjointRolesAxioms;
	}

	public Set<AsymmetricAxiom> getAsymmetricAxioms() {
		return asymmetricAxioms;
	}

	public void setAsymmetricAxioms(Set<AsymmetricAxiom> asymmetricAxioms) {
		this.asymmetricAxioms = asymmetricAxioms;
	}

	public Set<ReflexiveAxiom> getReflexiveAxioms() {
		return reflexiveAxioms;
	}

	public void setReflexiveAxioms(Set<ReflexiveAxiom> reflexiveAxioms) {
		this.reflexiveAxioms = reflexiveAxioms;
	}

	public Set<IrreflexiveAxiom> getIrreflexiveAxioms() {
		return irreflexiveAxioms;
	}

	public void setIrreflexiveAxioms(Set<IrreflexiveAxiom> irreflexiveAxioms) {
		this.irreflexiveAxioms = irreflexiveAxioms;
	}

	@Override
	public String toString() {
		String s = "Concept Inclusions Axioms: \n";
		for (ConceptInclusionAxiom ax : conceptInclusions) {
			s += ax + "\n";
		}
		s += "Role Inclusions Axioms: \n";
		for (RoleInclusionAxiom ax : roleInclusions) {
			s += ax + "\n";
		}
		s += "Transitivity Axioms: \n";
		for (TransitivityAxiom ax : transitivityAxioms) {
			s += ax + "\n";
		}
		s += "Disjoint Roles Axioms: \n";
		for (DisjointRolesAxiom ax : disjointRolesAxioms) {
			s += ax + "\n";
		}
		s += "Asymmetric Axioms: \n";
		for (AsymmetricAxiom ax : asymmetricAxioms) {
			s += ax + "\n";
		}
		s += "Reflexive Axioms: \n";
		for (ReflexiveAxiom ax : reflexiveAxioms) {
			s += ax + "\n";
		}
		s += "Irreflexive Axioms: \n";
		for (IrreflexiveAxiom ax : irreflexiveAxioms) {
			s += ax + "\n";
		}
		
		return s;
	}


}
