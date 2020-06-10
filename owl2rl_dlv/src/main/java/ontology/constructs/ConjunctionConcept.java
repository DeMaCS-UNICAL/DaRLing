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

import java.util.*;

public class ConjunctionConcept extends Concept {

	private List<Concept> concepts;
	
	public ConjunctionConcept() {
		super();
		concepts = new LinkedList<Concept>();
	}

	public ConjunctionConcept(List<Concept> concepts) {
		super();
		this.concepts = concepts;
	}

	public List<Concept> getConcepts() {
		return concepts;
	}
	
	public void setConcepts(List<Concept> concepts) {
		this.concepts = concepts;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConjunctionConcept) {
			ConjunctionConcept andCon = (ConjunctionConcept) obj;
			Set<Concept> thisConSet = new HashSet<Concept>();
			Set<Concept> andConSet = new HashSet<Concept>();
			for (Concept concept : concepts) {
				thisConSet.add(concept);
			}
			for (Concept concept : andCon.getConcepts()) {
				andConSet.add(concept);
			}
			return thisConSet.equals(andConSet);
		}
		return false;
	}

	@Override
	public String toString() {
		String s = "";
		int conceptsCount = 0; 
		for (Concept concept : concepts) {
			conceptsCount++;
			s += concept;
			if (conceptsCount < concepts.size()) {
				s += "_AND_";
			}
		}
		return s;
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
	public ConjunctionConcept getStructuralTransformation() {
		ConjunctionConcept st = new ConjunctionConcept();
		for (Concept c : this.concepts) {
			st.getConcepts().add(c.getFreshAtomicConcept());
		}
		return st;
	}

}
