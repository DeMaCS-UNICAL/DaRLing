package ontology.tbox;

import ontology.constructs.*;

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
	
//	public boolean isInNormalFormInOneStep() {
//		if (this.subConcept.isAtomic()) { 
//			if (this.superConcept instanceof NegatedConcept) {
//				NegatedConcept concept = (NegatedConcept) this.superConcept;
//				if (concept.getConcept() instanceof AtomicConcept) {
//					return true;
//				}
//				return false;
//			}
//			else if (this.superConcept instanceof ConjunctionConcept) {
//				ConjunctionConcept concept = (ConjunctionConcept) this.subConcept;
//				for (Concept c : concept.getConcepts()) {
//					if (!c.isAtomic()) {
//						return false;
//					}
//				}
//				return true;
//			}
//		}
//		if (this.superConcept.isAtomic()) {
//			if (this.subConcept instanceof DisjunctionConcept) {
//				DisjunctionConcept concept = (DisjunctionConcept) this.subConcept;
//				for (Concept c : concept.getConcepts()) {
//					if (!c.isAtomic()) {
//						return false;
//					}
//				}
//				return true;
//			}
//			if (this.subConcept instanceof ExistentialConcept) {
//				ExistentialConcept concept = (ExistentialConcept) this.subConcept;
//				if (concept.getConcept().isAtomic()) {
//					return true;
//				}
//				return false;
//			}
//			if (this.subConcept instanceof MinCardinalityConcept) {
//				MinCardinalityConcept concept = (MinCardinalityConcept) this.subConcept;
//				if (concept.getConcept().isAtomic() && concept.getMinCardinality() == 1) {
//					return true;
//				}
//				return false;
//			}
//		}
//		return false; 
//	}
	
	public boolean isInNormalFormInOneStep() {
		return this.isOfTypeA() || this.isOfTypeB() || this.isOfTypeC() || this.isOfTypeD() || this.isOfTypeE();
	}
	
	private boolean isOfTypeA() {
		if (this.subConcept.isConjunctionOfAtomicConcept()) {
			if (this.superConcept instanceof NegatedConcept) {
				NegatedConcept concept = (NegatedConcept) this.superConcept;
				return concept.getConcept().isConjunctionOfAtomicConcept();
			}
		}
		return false;
	}
	
	private boolean isOfTypeB() {
		return this.subConcept.isDisjunctionOfAtomicConcept() && this.superConcept.isAtomic();
	}
	
	private boolean isOfTypeC() {
		if (this.superConcept.isAtomic()) {
			if (this.subConcept instanceof ExistentialConcept) {
				ExistentialConcept concept = (ExistentialConcept) this.subConcept;
				return concept.getConcept().isConjunctionOfAtomicConcept();
			}
		}
		return false;
	}
	
	private boolean isOfTypeD() {
		if (this.superConcept.isAtomic()) {
			if (this.subConcept instanceof MinCardinalityConcept) {
				MinCardinalityConcept concept = (MinCardinalityConcept) this.subConcept;
				return concept.getMinCardinality() == 1 && concept.getConcept().isConjunctionOfAtomicConcept();
			}
		}
		return false;
	}
	
	private boolean isOfTypeE() {
		return this.subConcept.isConjunctionOfAtomicConcept() && this.superConcept.isConjunctionOfAtomicConcept();
	}

}


