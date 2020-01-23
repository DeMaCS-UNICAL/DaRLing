package ontology.tbox;

import ontology.constructs.Concept;

public class ConceptInclusionAxiom {
	
	private Concept subConcept;
	private Concept superConcept;

	public ConceptInclusionAxiom() {}

	public ConceptInclusionAxiom(Concept subConcept, Concept superConcept) {
		this.subConcept = subConcept;
		this.superConcept = superConcept;
	}

	public Concept getSubConcept() {
		return subConcept;
	}

	public void setSubConcept(Concept subConcept) {
		this.subConcept = subConcept;
	}

	public Concept getSuperConcept() {
		return superConcept;
	}

	public void setSuperConcept(Concept superConcept) {
		this.superConcept = superConcept;
	}

	@Override
	public String toString() {
		return subConcept + " <SUB_CONCEPT_OF> " + superConcept;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConceptInclusionAxiom) {
			ConceptInclusionAxiom ax = (ConceptInclusionAxiom) obj;
			return subConcept.equals(ax.getSubConcept()) && superConcept.equals(ax.getSuperConcept());
		}
		return false;
	}

}
