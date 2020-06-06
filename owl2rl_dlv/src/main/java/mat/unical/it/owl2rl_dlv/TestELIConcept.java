package mat.unical.it.owl2rl_dlv;

import datalog.costructs.Body;
import datalog.costructs.Head;
import datalog.costructs.Literal;
import datalog.costructs.Rule;
import datalog.costructs.Term;
import datalog.costructs.Variable;
import ontology.constructs.AtomicConcept;
import ontology.constructs.BottomConcept;
import ontology.constructs.Concept;
import ontology.constructs.ConjunctionConcept;
import ontology.constructs.ExistentialConcept;
import ontology.constructs.MinCardinalityConcept;
import ontology.constructs.Role;
import ontology.constructs.TopConcept;

public class TestELIConcept {

	public static void main(String[] args) {
		
		AtomicConcept a = new AtomicConcept("A");
		AtomicConcept b = new AtomicConcept("B");
		AtomicConcept c = new AtomicConcept("C");
		AtomicConcept d = new AtomicConcept("D");
		AtomicConcept e = new AtomicConcept("E");
		AtomicConcept f = new AtomicConcept("F");
		
		Role r = new Role("R");
		Role s = new Role("S");
		Role t = new Role("T");
		Role u = new Role("U");
		
		ConjunctionConcept conjCD = new ConjunctionConcept();
		conjCD.getConcepts().add(c);
		conjCD.getConcepts().add(d);
		ExistentialConcept exS = new ExistentialConcept(s, conjCD);
		ExistentialConcept exR = new ExistentialConcept(r, exS);
		
		ExistentialConcept exU = new ExistentialConcept(u, f);
		ConjunctionConcept conjEexU = new ConjunctionConcept();
		conjEexU.getConcepts().add(e);
		conjEexU.getConcepts().add(exU);
		MinCardinalityConcept minT = new MinCardinalityConcept(1, t, conjEexU);
		
		ConjunctionConcept eliConcept = new ConjunctionConcept();
		eliConcept.getConcepts().add(a);
		eliConcept.getConcepts().add(exR);
		eliConcept.getConcepts().add(minT);
		eliConcept.getConcepts().add(b);
		
//		System.out.println(eliConcept);
		
		if (eliConcept.isELIConcept()) {
			System.out.println(eliConcept);
			Body body = new Body();
			getBodyLiteralsfromELIConcept(body, eliConcept, 0, new Variable("X")); 
			Head head = new Head();
			head.setHeadAtom(getLiteralFromAtomicConcept(a, new Variable("X")));
			Rule rule = new Rule(head, body);
			System.out.println("Added Datalog rule: " + rule);	
		}
		
		
		

	}
	
	private static void getBodyLiteralsfromELIConcept(Body body, Concept eliConcept, int clause, Variable sharedVariable) {
		if (eliConcept instanceof AtomicConcept) {
			Literal l = getLiteralFromAtomicConcept(eliConcept, sharedVariable);
			body.getBodyLiterals().add(l);
		}
		else if (eliConcept instanceof ConjunctionConcept) {
			ConjunctionConcept conj = (ConjunctionConcept) eliConcept;
			for (Concept c : conj.getConcepts()) {
				getBodyLiteralsfromELIConcept(body, c, clause, sharedVariable);
				clause++;
			}
		}
		else if (eliConcept instanceof ExistentialConcept) {
			ExistentialConcept exCon = (ExistentialConcept) eliConcept;
			Variable newVariable = new Variable(sharedVariable.getName() + "_" + clause);
			Literal l = getLiteralFromRole(exCon.getRole(), sharedVariable, newVariable);
			body.getBodyLiterals().add(l);
			getBodyLiteralsfromELIConcept(body, exCon.getConcept(), 0, newVariable);
		}
		else if (eliConcept instanceof MinCardinalityConcept && ((MinCardinalityConcept) eliConcept).getMinCardinality() == 1) {
			MinCardinalityConcept minCon = (MinCardinalityConcept) eliConcept;
			Variable newVariable = new Variable(sharedVariable.getName() + "_" + clause);
			Literal l = getLiteralFromRole(minCon.getRole(), sharedVariable, newVariable);
			body.getBodyLiterals().add(l);
			getBodyLiteralsfromELIConcept(body, minCon.getConcept(), 0, newVariable);
		}
	}
	
	private static Literal getLiteralFromRole(Role role, Term t1, Term t2) {
		Literal literal = new Literal(role.toDatalogString(),2);
		if (role.isInverse()) {
			literal.getArguments().add(t2);
			literal.getArguments().add(t1);
		} else {
			literal.getArguments().add(t1);
			literal.getArguments().add(t2);
		}
		return literal;
	}
	
	private static Literal getLiteralFromRole(Role role, Term t) {
		Literal literal = new Literal(role.toDatalogString(),2);
		literal.getArguments().add(t);
		literal.getArguments().add(t);
		return literal;
	}
	
	private static Literal getLiteralFromAtomicConcept(Concept concept, Term t) {
		if (concept instanceof TopConcept || concept instanceof BottomConcept){
			return new Literal("",0);
		} else {
			Literal literal = new Literal(concept.toString(),1);
			literal.getArguments().add(t);
			return literal;
		}
	}

}
