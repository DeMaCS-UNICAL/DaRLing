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
	
	public boolean isELIConcept() {
		if (this instanceof AtomicConcept) {
			return true;
		} 
		if (this instanceof ExistentialConcept) {
			ExistentialConcept existConcept = (ExistentialConcept) this;
			return existConcept.getConcept().isELIConcept();
		}
		if (this instanceof MinCardinalityConcept) {
			MinCardinalityConcept minCardConcept = (MinCardinalityConcept) this;
			if (minCardConcept.getMinCardinality() == 1) {
				return minCardConcept.getConcept().isELIConcept();
			}
		}
		if (this instanceof ConjunctionConcept) {
			ConjunctionConcept conjConcept = (ConjunctionConcept) this;
			for (Concept c : conjConcept.getConcepts()) {
				if (!c.isELIConcept()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
