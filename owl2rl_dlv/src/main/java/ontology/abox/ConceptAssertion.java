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
package ontology.abox;

import ontology.constructs.Concept;
import ontology.constructs.Individual;

public class ConceptAssertion {
	
	private Concept concept;
	private Individual individual;
	
	public ConceptAssertion() {}

	public ConceptAssertion(Concept concept, Individual individual) {
		super();
		this.concept = concept;
		this.individual = individual;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	@Override
	public String toString() {
		return concept + "(" + individual + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConceptAssertion) {
			ConceptAssertion ca = (ConceptAssertion) obj;
			return concept.equals(ca.getConcept()) && individual.equals(ca.getIndividual());
		}
		return false;
	}
	
	
}
