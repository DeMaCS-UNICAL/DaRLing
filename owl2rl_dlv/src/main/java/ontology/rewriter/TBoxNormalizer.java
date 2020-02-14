package ontology.rewriter;

import java.util.HashSet;
import java.util.Set;

import ontology.constructs.*;
import ontology.tbox.ConceptInclusionAxiom;

public class TBoxNormalizer {
	
	private Set<ConceptInclusionAxiom> axioms;
	private Set<ConceptInclusionAxiom> trasformedAxioms;
	private Set<ConceptInclusionAxiom> normalizedAxioms;
	private Set<ConceptInclusionAxiom> subELIConceptAxioms;
	private Set<ConceptInclusionAxiom> specialAxioms;

	private Set<Concept> scannedConcepts = new HashSet<Concept>();
	
	
	public TBoxNormalizer() {
		normalizedAxioms = new HashSet<ConceptInclusionAxiom>();
		trasformedAxioms = new HashSet<ConceptInclusionAxiom>();
		specialAxioms = new HashSet<ConceptInclusionAxiom>();
		subELIConceptAxioms = new HashSet<ConceptInclusionAxiom>();
	}
	
	public TBoxNormalizer(Set<ConceptInclusionAxiom> axioms) {
		this.axioms = axioms;
		normalizedAxioms = new HashSet<ConceptInclusionAxiom>();
		trasformedAxioms = new HashSet<ConceptInclusionAxiom>();
		specialAxioms = new HashSet<ConceptInclusionAxiom>();
	}
	
	public Set<ConceptInclusionAxiom> getSpecialAxioms() {
		return specialAxioms;
	}
	
	public Set<ConceptInclusionAxiom> getTrasformedAxioms() {
		return trasformedAxioms;
	}
	
	public Set<ConceptInclusionAxiom> getNormalizedAxioms() {
		return normalizedAxioms;
	}

	public void normalizeAxioms() {
		
		applyStructuralTransformation();
		
		for (ConceptInclusionAxiom axiom : trasformedAxioms) {
			Concept subConcept = axiom.getSubConcept();
			Concept superConcept = axiom.getSuperConcept();
			if (!(superConcept instanceof ExistentialConcept)) {
				if (subConcept instanceof TopConcept) {
					specialAxioms.add(axiom);
				}
				if (superConcept instanceof NegatedConcept) {
					NegatedConcept neg = (NegatedConcept) superConcept;
					ConjunctionConcept newSubConcept = new ConjunctionConcept();
					newSubConcept.getConcepts().add(subConcept);
					newSubConcept.getConcepts().add((neg.getConcept()));
					normalizedAxioms.add(new ConceptInclusionAxiom(newSubConcept, new BottomConcept()));
				}
				else if (subConcept instanceof DisjunctionConcept) {
					DisjunctionConcept disj = (DisjunctionConcept) subConcept;
					for (Concept concept : disj.getConcepts()) {
						normalizedAxioms.add(new ConceptInclusionAxiom(concept,superConcept));
					}
				}
				else if (subConcept instanceof ExistentialConcept) {
					ExistentialConcept exCon = (ExistentialConcept) subConcept;
					if (exCon.getConcept() instanceof TopConcept) {
						specialAxioms.add(new ConceptInclusionAxiom(exCon.getConcept(), new UniversalConcept(exCon.getRole().getInverseRole(), superConcept)));
					} else {
						normalizedAxioms.add(new ConceptInclusionAxiom(exCon.getConcept(), new UniversalConcept(exCon.getRole().getInverseRole(), superConcept)));
					}
				}
				else if (subConcept instanceof MinCardinalityConcept) {
					MinCardinalityConcept minCon = (MinCardinalityConcept) subConcept;
					if (minCon.getMinCardinality() == 1) {
						if (minCon.getConcept() instanceof TopConcept) {
							specialAxioms.add(new ConceptInclusionAxiom(minCon.getConcept(), new UniversalConcept(minCon.getRole().getInverseRole(), superConcept)));
						} else {
							normalizedAxioms.add(new ConceptInclusionAxiom(minCon.getConcept(), new UniversalConcept(minCon.getRole().getInverseRole(), superConcept)));
						}
					}
				}
				else if (superConcept instanceof ConjunctionConcept) {
					ConjunctionConcept conj = (ConjunctionConcept) superConcept;
					for (Concept concept : conj.getConcepts()) {
						normalizedAxioms.add(new ConceptInclusionAxiom(subConcept,concept));
					}
				}
				else {
					normalizedAxioms.add(axiom);
				}
			}
		}
		
	}
	
	private void applyStructuralTransformation() {
		
		
		for (ConceptInclusionAxiom conceptInclusion : axioms) {
			Concept subConcept = conceptInclusion.getSubConcept();
			Concept superConcept = conceptInclusion.getSuperConcept();
			
			if(!(subConcept instanceof UnmanagedConcept) && !(superConcept instanceof UnmanagedConcept)) {
				
				if (subConcept.isAtomic() && superConcept.isAtomic()) {
					trasformedAxioms.add(conceptInclusion);
				}
				else if (subConcept instanceof TopConcept) {
					specialAxioms.add(conceptInclusion);
				}
				else if (conceptInclusion.isInNormalForm() || conceptInclusion.isInNormalFormInOneStep()) {
					trasformedAxioms.add(conceptInclusion);
				}
				else if (subConcept.isAtomic()) {
					Concept newSuperConcept = superConcept.getFreshAtomicConcept();
					trasformedAxioms.add(new ConceptInclusionAxiom(subConcept, newSuperConcept));
					addAxiomsFromConcept(superConcept);
				}
				else if (subConcept.isELIConcept() && superConcept instanceof AtomicConcept) {
					subELIConceptAxioms.add(conceptInclusion);
				}
				else if (superConcept.isAtomic()) {
					Concept newSubConcept = subConcept.getFreshAtomicConcept();
					trasformedAxioms.add(new ConceptInclusionAxiom(newSubConcept, superConcept));
					addAxiomsFromConcept(subConcept);
				}
				else {
					Concept newSubConcept = subConcept.getFreshAtomicConcept();
					Concept newSuperConcept = superConcept.getFreshAtomicConcept();
					trasformedAxioms.add(new ConceptInclusionAxiom(newSubConcept, newSuperConcept));
					addAxiomsFromConcept(subConcept);
					addAxiomsFromConcept(superConcept);
				}
				
			}
			
				
		}

		
	}
	
	private boolean occursPositivelyInTBox(Concept concept, Set<ConceptInclusionAxiom> cis) {
		for (ConceptInclusionAxiom ax : cis) {
			Concept subConcept = ax.getSubConcept();
			Concept superConcept = ax.getSuperConcept();
			if (occursPositivelyInConcept(concept,superConcept))
				return true;
			if (occursNegativelyInConcept(concept,subConcept))
				return true;
		}
		return false;
	}
	
	private boolean occursNegativelyInTBox(Concept concept, Set<ConceptInclusionAxiom> cis) {
		for (ConceptInclusionAxiom ax : cis) {
			Concept subConcept = ax.getSubConcept();
			Concept superConcept = ax.getSuperConcept();
			if (occursNegativelyInConcept(concept,superConcept))
				return true;
			if (occursPositivelyInConcept(concept,subConcept))
				return true;
		}
		return false;
	}
	
	private boolean occursPositivelyInConcept(Concept c1, Concept c2) {
		
		if (c1.equals(c2))
			return true;
		if (c2 instanceof NegatedConcept) {
			NegatedConcept c = (NegatedConcept) c2;
			return occursNegativelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof ConjunctionConcept) {
			ConjunctionConcept c = (ConjunctionConcept) c2;
			for (Concept concept : c.getConcepts()) {
				if (occursPositivelyInConcept(c1, concept))
					return true;
			}
		}
		if (c2 instanceof DisjunctionConcept) {
			DisjunctionConcept c = (DisjunctionConcept) c2;
			for (Concept concept : c.getConcepts()) {
				if (occursPositivelyInConcept(c1, concept))
					return true;
			}
		}
		if (c2 instanceof ExistentialConcept) {
			ExistentialConcept c = (ExistentialConcept) c2;
			return occursPositivelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof UniversalConcept) {
			UniversalConcept c = (UniversalConcept) c2;
			return occursPositivelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof MinCardinalityConcept) {
			MinCardinalityConcept c = (MinCardinalityConcept) c2;
			return occursPositivelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof MaxCardinalityConcept) {
			MaxCardinalityConcept c = (MaxCardinalityConcept) c2;
			return occursNegativelyInConcept(c1, c.getConcept());
		}	
		return false;
		
	}
	
	private boolean occursNegativelyInConcept(Concept c1, Concept c2) {
		
		if (c1.equals(c2))
			return false;
		if (c2 instanceof NegatedConcept) {
			NegatedConcept c = (NegatedConcept) c2;
			return occursPositivelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof ConjunctionConcept) {
			ConjunctionConcept c = (ConjunctionConcept) c2;
			for (Concept concept : c.getConcepts()) {
				if (occursNegativelyInConcept(c1, concept))
					return true;
			}
		}
		if (c2 instanceof DisjunctionConcept) {
			DisjunctionConcept c = (DisjunctionConcept) c2;
			for (Concept concept : c.getConcepts()) {
				if (occursNegativelyInConcept(c1, concept))
					return true;
			}
		}
		if (c2 instanceof ExistentialConcept) {
			ExistentialConcept c = (ExistentialConcept) c2;
			return occursNegativelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof UniversalConcept) {
			UniversalConcept c = (UniversalConcept) c2;
			return occursNegativelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof MinCardinalityConcept) {
			MinCardinalityConcept c = (MinCardinalityConcept) c2;
			return occursNegativelyInConcept(c1, c.getConcept());
		}
		if (c2 instanceof MaxCardinalityConcept) {
			MaxCardinalityConcept c = (MaxCardinalityConcept) c2;
			return occursPositivelyInConcept(c1, c.getConcept());
		}	
		return false;
		
	}
	
	private void addAxiomsFromConcept(Concept concept) {
		if(!(concept.isAtomic()) && scannedConcepts.add(concept)) {
			if (occursPositivelyInTBox(concept, axioms))
				trasformedAxioms.add(new ConceptInclusionAxiom(concept.getFreshAtomicConcept(),concept.getStructuralTransformation()));
			if (occursNegativelyInTBox(concept, axioms))
				trasformedAxioms.add(new ConceptInclusionAxiom(concept.getStructuralTransformation(),concept.getFreshAtomicConcept()));
			
			
			if (concept instanceof NegatedConcept) {
				NegatedConcept c = (NegatedConcept) concept;
				addAxiomsFromConcept(c.getConcept());
			}
			if (concept instanceof ConjunctionConcept) {
				ConjunctionConcept conj = (ConjunctionConcept) concept;
				for (Concept c : conj.getConcepts()) {
					addAxiomsFromConcept(c);
				}
			}
			if (concept instanceof DisjunctionConcept) {
				DisjunctionConcept disj = (DisjunctionConcept) concept;
				for (Concept c : disj.getConcepts()) {
					addAxiomsFromConcept(c);
				}
			}
			if (concept instanceof ExistentialConcept) {
				ExistentialConcept c = (ExistentialConcept) concept;
				addAxiomsFromConcept(c.getConcept());
			}
			if (concept instanceof UniversalConcept) {
				UniversalConcept c = (UniversalConcept) concept;
				addAxiomsFromConcept(c.getConcept());
			}
			if (concept instanceof MinCardinalityConcept) {
				MinCardinalityConcept c = (MinCardinalityConcept) concept;
				addAxiomsFromConcept(c.getConcept());
			}
			if (concept instanceof MaxCardinalityConcept) {
				MaxCardinalityConcept c = (MaxCardinalityConcept) concept;
				addAxiomsFromConcept(c.getConcept());
			} 
			
			
		}
	}
	
	
}
