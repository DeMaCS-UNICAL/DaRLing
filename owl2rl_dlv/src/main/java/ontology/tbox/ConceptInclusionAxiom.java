package ontology.tbox;

import ontology.constructs.Concept;
import ontology.constructs.ConjunctionConcept;

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
	
	public boolean isInNormalForm() {
		if (this.subConcept.isAtomic() && this.superConcept.isSimple()) {
			return true;
		} else if (this.subConcept instanceof ConjunctionConcept) {
			ConjunctionConcept concept = (ConjunctionConcept) this.subConcept;
			for (Concept c : concept.getConcepts()) {
				if (!c.isAtomic()) {
					return false;
				}
			}
			if (this.superConcept.isSimple()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInNormalFormInOneStep() {
		return false; //TODO: complete and commit
	}

}
