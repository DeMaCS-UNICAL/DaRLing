package ontology.constructs;

public abstract class Concept {
	
	public abstract boolean equals(Object obj);
	public abstract String toString();
	public abstract boolean isAtomic();
	public abstract Concept getFreshAtomicConcept();
	public abstract Concept getStructuralTransformation();
	
	public boolean isManaged() {
		if (this instanceof UnmanagedConcept) 
			return false;
		else if (this.isAtomic())
			return true;
		else if (this instanceof NegatedConcept) { 
			NegatedConcept nc = (NegatedConcept) this;
			return nc.getConcept().isManaged();
		}
		else if (this instanceof ConjunctionConcept) {
			ConjunctionConcept conj = (ConjunctionConcept) this;
			for (Concept c : conj.getConcepts()) {
				if (!c.isManaged())
					return false;
			}
			return true;
		}
		else if (this instanceof DisjunctionConcept) {
			DisjunctionConcept disj = (DisjunctionConcept) this;
			for (Concept c : disj.getConcepts()) {
				if (!c.isManaged())
					return false;
			}
			return true;
		}
		else if (this instanceof ExistentialConcept) {
			ExistentialConcept c = (ExistentialConcept) this;
			return c.getConcept().isManaged();
		}
		else if (this instanceof UniversalConcept) {
			UniversalConcept c = (UniversalConcept) this;
			return c.getConcept().isManaged();
		}
		else if (this instanceof MinCardinalityConcept) {
			MinCardinalityConcept c = (MinCardinalityConcept) this;
			return c.getConcept().isManaged();
		}
		else if (this instanceof MaxCardinalityConcept) {
			MaxCardinalityConcept c = (MaxCardinalityConcept) this;
			return c.getConcept().isManaged();
		}
		return false;
	}
	
	public boolean isSimple() {
		if (this instanceof BottomConcept)
			return true;
		if (this instanceof AtomicConcept)
			return true;
		if (this instanceof ExistentialConcept) {
			ExistentialConcept concept = (ExistentialConcept) this;
			if (concept.getConcept() instanceof AtomicConcept) {
				return true;
			} else {
				return false;
			}
		}
		if (this instanceof UniversalConcept) {
			UniversalConcept concept = (UniversalConcept) this;
			if (concept.getConcept() instanceof AtomicConcept) {
				return true;
			} else {
				return false;
			}
		}
		if (this instanceof MaxCardinalityConcept) {
			MaxCardinalityConcept concept = (MaxCardinalityConcept) this;
			if ((concept.getConcept() instanceof AtomicConcept) && (concept.getMaxCardinality() == 1)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public boolean isConjunctionOfAtomicConcept() {
		if (this.isAtomic()) {
			return true;
		}
		if (this instanceof ConjunctionConcept) {
			ConjunctionConcept concept = (ConjunctionConcept) this;
			for (Concept c : concept.getConcepts()) {
				if (!c.isAtomic()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean isDisjunctionOfAtomicConcept() {
		if (this.isAtomic()) {
			return true;
		}
		if (this instanceof DisjunctionConcept) {
			DisjunctionConcept concept = (DisjunctionConcept) this;
			for (Concept c : concept.getConcepts()) {
				if (!c.isAtomic()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
