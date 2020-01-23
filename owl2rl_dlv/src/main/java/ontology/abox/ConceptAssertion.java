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
