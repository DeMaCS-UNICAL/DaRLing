package ontology.constructs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DisjunctionConcept extends Concept {

	private List<Concept> concepts;
	
	public DisjunctionConcept() {
		super();
		concepts = new LinkedList<Concept>(); 
	}
	
	public DisjunctionConcept(List<Concept> concepts) {
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
		if (obj instanceof DisjunctionConcept) {
			DisjunctionConcept orCon = (DisjunctionConcept) obj;
			Set<Concept> thisConSet = new HashSet<Concept>();
			Set<Concept> orConSet = new HashSet<Concept>();
			for (Concept concept : concepts) {
				thisConSet.add(concept);
			}
			for (Concept concept : orCon.getConcepts()) {
				orConSet.add(concept);
			}
			return thisConSet.equals(orConSet);
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
				s += "_OR_";
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
	public DisjunctionConcept getStructuralTransformation() {
		DisjunctionConcept st = new DisjunctionConcept();
		for (Concept c : this.concepts) {
			st.getConcepts().add(c.getFreshAtomicConcept());
		}
		return st;
	}

}
