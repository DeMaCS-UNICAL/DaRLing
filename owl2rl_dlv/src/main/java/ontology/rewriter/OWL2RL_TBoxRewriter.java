package ontology.rewriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import datalog.costructs.*;
import ontology.constructs.*;
import ontology.tbox.*;

public class OWL2RL_TBoxRewriter {

	private Program p;
	
	public OWL2RL_TBoxRewriter() {
		p = new Program();
	}
	
	public Program getDatalogProgram() {
		return p;
	}
	
	public void rewrite (TBox tbox) throws FileNotFoundException {
		
		// Creating Files object that represent the disk file. 
        PrintStream ontTranslationFile = new PrintStream(new File("OntologyTranslation.txt"));
        PrintStream errorInTranslationFile = new PrintStream(new File("TranslationErrors.txt"));
        
  
        // Store current System.out before assigning a new value 
//        PrintStream console = System.out; 
  
        // Assign ontTranslationFile to output stream 
        System.setOut(ontTranslationFile); 
         
		
		Set<Rule> rules = p.getRules();
		
		TBoxNormalizer normalizer = new TBoxNormalizer(tbox.getConceptInclusions());
		normalizer.normalizeAxioms();
		
		Set<ConceptInclusionAxiom> normalizedAxioms = normalizer.getNormalizedAxioms();
		Set<ConceptInclusionAxiom> subELIConceptAxioms = normalizer.getSubELIConceptAxioms();
		Set<ConceptInclusionAxiom> specialAxioms = normalizer.getSpecialAxioms();
		
		// Normal Axioms
		for (ConceptInclusionAxiom subConceptOfAxiom : normalizedAxioms) {
			
			System.out.println("Translate normalized axiom: " + subConceptOfAxiom);
		
			Concept subConcept =  subConceptOfAxiom.getSubConcept();
			Concept superConcept = subConceptOfAxiom.getSuperConcept();
			
			if (subConcept instanceof BottomConcept || superConcept instanceof TopConcept) { 
				System.setOut(errorInTranslationFile);
				System.out.println("Error during translation. Axiom: " + subConceptOfAxiom);
				System.setOut(ontTranslationFile);
			}
			else if (subConcept instanceof TopConcept) {
				specialAxioms.add(subConceptOfAxiom);
			}
			else {
				Body body = new Body();
				Head head = new Head();
				
				Variable x = new Variable("X");
				Variable y = new Variable("Y");
				Variable y1 = new Variable("Y1");
				
				if (subConcept.isAtomic()) {
					body.getBodyLiterals().add(getLiteralFromAtomicConcept(subConcept, x));
				} else if (subConcept instanceof ConjunctionConcept) {
					ConjunctionConcept conj = (ConjunctionConcept) subConcept;
					for (Concept c : conj.getConcepts()) {
						body.getBodyLiterals().add(getLiteralFromAtomicConcept(c, x));
					}
				} 
				
				if (superConcept.isAtomic()) {
					head.setHeadAtom(getLiteralFromAtomicConcept(superConcept, x));
				} else if (superConcept instanceof UniversalConcept) {
					head.setHeadAtom(getLiteralFromAtomicConcept(((UniversalConcept) superConcept).getConcept(), y));
					body.getBodyLiterals().add(getLiteralFromRole(((UniversalConcept) superConcept).getRole(), x, y));
				} else if (superConcept instanceof MaxCardinalityConcept) {
					if (((MaxCardinalityConcept) superConcept).getMaxCardinality() == 1) {
						body.getBodyLiterals().add(getLiteralFromRole(((MaxCardinalityConcept)superConcept).getRole(), x, y));
						body.getBodyLiterals().add(getLiteralFromRole(((MaxCardinalityConcept)superConcept).getRole(), x, y1));
						body.getBodyLiterals().add(getLiteralFromAtomicConcept(((MaxCardinalityConcept) superConcept).getConcept(),y));
						body.getBodyLiterals().add(getLiteralFromAtomicConcept(((MaxCardinalityConcept) superConcept).getConcept(),y1));
						body.getInequalities().add(new NotEqualToLiteral(y, y1));
					} else if (((MaxCardinalityConcept) superConcept).getMaxCardinality() == 0){
						body.getBodyLiterals().add(getLiteralFromRole(((MaxCardinalityConcept)superConcept).getRole(), x, y));
						body.getBodyLiterals().add(getLiteralFromAtomicConcept(((MaxCardinalityConcept) superConcept).getConcept(),y));
					}
				} 
					
				Rule rule = new Rule(head, body);
				rules.add(rule);
				if (superConcept instanceof BottomConcept) {
					System.out.println("Added Datalog constraint: " + rule);
				}
				else {
					System.out.println("Added Datalog rule: " + rule);
				}
					
			}
		}
		
		// ELI-SubConcept Axioms
		for (ConceptInclusionAxiom ax : subELIConceptAxioms) {
			System.out.println("Translate ELI-SubConcept axiom: " + ax);
			System.out.println("ONGOING...");								// TODO: implement method to translate!
		}
		
		// Special Axioms
		for (ConceptInclusionAxiom specialAx : specialAxioms) {
			System.out.println("Translate special axiom: " + specialAx);
			if (specialAx.getSuperConcept() instanceof UniversalConcept) {
				UniversalConcept superConcept = (UniversalConcept) specialAx.getSuperConcept();
				Role r = superConcept.getRole();
				Concept c = superConcept.getConcept();
				if (c instanceof AtomicConcept) {
					Body body = new Body();
					Head head = new Head();
					
					Variable x = new Variable("_");
					Variable y = new Variable("Y");
					
					body.getBodyLiterals().add(getLiteralFromRole(r, x, y));
					head.setHeadAtom(getLiteralFromAtomicConcept(c, y));
					
					Rule rule = new Rule(head, body);
					rules.add(rule);
					
					System.out.println("Added Datalog rule: " + rule);
					
				} else {
					System.setOut(errorInTranslationFile);
					System.out.println("Unmanaged traslation: " + specialAx);
					System.setOut(ontTranslationFile);
				}
			} 
			else if (specialAx.getSuperConcept() instanceof MaxCardinalityConcept) {
				MaxCardinalityConcept superConcept = (MaxCardinalityConcept) specialAx.getSuperConcept();
				int m = superConcept.getMaxCardinality();
				Role r = superConcept.getRole();
				Concept c = superConcept.getConcept();
				if (m == 1 && c instanceof TopConcept) {
					Body body = new Body();
					Head head = new Head();
					
					Variable x = new Variable("X");
					Variable y1 = new Variable("Y1");
					Variable y2 = new Variable("Y2");
					
					Literal sameAs = new Literal("sameAs", 2);
					sameAs.getArguments().add(y1);
					sameAs.getArguments().add(y2);
					
					NotEqualToLiteral neq = new NotEqualToLiteral(y1, y2);
					
					body.getBodyLiterals().add(getLiteralFromRole(r, x, y1));
					body.getBodyLiterals().add(getLiteralFromRole(r, x, y2));
					body.getInequalities().add(neq);
					head.setHeadAtom(sameAs);
					
					Rule rule = new Rule(head, body);
					rules.add(rule);
					
					System.out.println("Added Datalog rule: " + rule);
					
				} else {
					System.out.println("Unmanaged traslation: " + specialAx);
				}
			} 
			else {
				System.out.println("Unmanaged traslation: " + specialAx);
			}
		}
		
		// Role Inclusion Rewriting
		for (RoleInclusionAxiom roleInclusion : tbox.getRoleInclusions()) {
			
			System.out.println("Translate axiom: " + roleInclusion);
			
			Role subRole = roleInclusion.getSubRole();
			Role superRole = roleInclusion.getSuperRole();
			Variable x = new Variable("X");
			Variable y = new Variable("Y");
			// Body
			Body body = new Body();
			body.getBodyLiterals().add(getLiteralFromRole(subRole,x,y));
			// Head
			Head head = new Head(getLiteralFromRole(superRole,x,y));
			
			Rule rule = new Rule(head, body);
			rules.add(rule);
			
			System.out.println("Added Datalog rule: " + rule);
			
		}
		
		// Disjoint Roles Rewriting
		for (DisjointRolesAxiom disjointRoles : tbox.getDisjointRolesAxioms()) {
			
			System.out.println("Translate axiom: " + disjointRoles);
			
			Role firstRole = disjointRoles.getRole1();
			Role secondRole = disjointRoles.getRole2();
			Variable x = new Variable("X");
			Variable y = new Variable("Y");
			// Head (void)
			Head voidHead = new Head();
			// Body
			Body body = new Body();
			body.getBodyLiterals().add(getLiteralFromRole(firstRole,x,y));
			body.getBodyLiterals().add(getLiteralFromRole(secondRole,x,y));
			
			Rule rule = new Rule(voidHead, body);
			rules.add(rule);
			
			System.out.println("Added Datalog rule: " + rule);
			
		}
		
		// Irreflexive Axioms Rewriting
		for (IrreflexiveAxiom irreflexiveRole : tbox.getIrreflexiveAxioms()) {
			
			System.out.println("Translate axiom: " + irreflexiveRole);
			
			Role role = irreflexiveRole.getRole();
			// Head (void)
			Head voidHead = new Head();
			// Body
			Body body = new Body();
			body.getBodyLiterals().add(getLiteralFromRole(role,new Variable("X")));
			
			Rule rule = new Rule(voidHead, body);
			rules.add(rule);
			
			System.out.println("Added Datalog rule: " + rule);
			
		}
		
		// Asymmetric Axioms Rewriting
		for (AsymmetricAxiom asymmetricRole : tbox.getAsymmetricAxioms()) {
			
			System.out.println("Translate axiom: " + asymmetricRole);
			
			Role role = asymmetricRole.getRole();
			Variable x = new Variable("X");
			Variable y = new Variable("Y");
			// Head (void)
			Head voidHead = new Head();
			// Body
			Body body = new Body();
			body.getBodyLiterals().add(getLiteralFromRole(role,x,y));
			body.getBodyLiterals().add(getLiteralFromRole(role,y,x));
			
			Rule rule = new Rule(voidHead, body);
			rules.add(rule);
			
			System.out.println("Added Datalog rule: " + rule);
			
		}
		
		// Transitivity Axioms Rewriting
		for (TransitivityAxiom transitivityRole : tbox.getTransitivityAxioms()) {
			
			System.out.println("Translate axiom: " + transitivityRole);
			
			Role role = transitivityRole.getRole();
			Variable x = new Variable("X");
			Variable y = new Variable("Y");
			Variable z = new Variable("Z");
			//Head
			Head head = new Head(getLiteralFromRole(role,x,z));
			// Body
			Body body = new Body();
			body.getBodyLiterals().add(getLiteralFromRole(role,x,y));
			body.getBodyLiterals().add(getLiteralFromRole(role,y,z));
			
			Rule rule = new Rule(head, body);
			rules.add(rule);
			
			System.out.println("Added Datalog rule: " + rule);
			
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
