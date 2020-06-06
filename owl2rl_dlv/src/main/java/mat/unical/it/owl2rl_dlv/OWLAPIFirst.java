package mat.unical.it.owl2rl_dlv;

import java.io.*;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import datalog.costructs.Program;
import datalog.costructs.Rule;
import exceptions.UnmanagedCostructException;
import ontology.rewriter.TBoxLoader;
import ontology.tbox.UnmanagedAxiom;
import ontology.rewriter.OWL2RL_TBoxRewriter;


public class OWLAPIFirst {

	public static void main(String[] args) throws OWLOntologyCreationException, UnmanagedCostructException, FileNotFoundException {

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File file = new File("univ-bench-RL.owl");
		OWLOntology ont = man.loadOntologyFromOntologyDocument(file);
		
		TBoxLoader tBoxLoader = new TBoxLoader(true);
		tBoxLoader.load(ont);
//		System.out.println("Unmanaged Axioms: ");
//		for (UnmanagedAxiom a : tBoxLoader.getUnmanagedAxioms()) {
//			System.out.println(a);
//		}
		
//		System.out.println("Concept Inclusions: " + tBoxLoader.getTBox().getConceptInclusions().size() + "\nRole Inclusions: " +tBoxLoader.getTBox().getRoleInclusions().size()
//				+ "\nDisjoint Roles Axioms: " + tBoxLoader.getTBox().getDisjointRolesAxioms().size() + "\nIrreflexive Axioms: " + tBoxLoader.getTBox().getIrreflexiveAxioms().size()
//				+ "\nAsymmetric Axioms: " + tBoxLoader.getTBox().getAsymmetricAxioms().size() + "\nTransitivity Axioms: " + tBoxLoader.getTBox().getTransitivityAxioms().size());
		
		OWL2RL_TBoxRewriter rewriter = new OWL2RL_TBoxRewriter();
		rewriter.rewrite(tBoxLoader.getTBox());
		
		PrintStream datalogTBoxFile = new PrintStream(new File("LUBM_Datalog_TBox.txt"));
		PrintStream constraintsFile = new PrintStream(new File("LUBM_Constraints.txt"));
		  
        // Store current System.out before assigning a new value 
//        PrintStream console = System.out; 
  
        // Assign ontTranslationFile to output stream 
		
		for (Rule rule : rewriter.getDatalogProgram().getRules()) {
			if (rule.getHead().getHeadAtom().getPredicateName() == "") {
				System.setOut(constraintsFile);
				System.out.println(rule);
			}
			else {
				System.setOut(datalogTBoxFile);
				System.out.println(rule);
			}
		}
         
		
//		System.out.println("Ontology Rewrited:");
//		System.out.println(rewriter.getDatalogProgram());
		
		
//		// Creating a File object that represents the disk file. 
//        PrintStream o = new PrintStream(new File("TranslatedOntology.txt")); 
//  
//        // Store current System.out before assigning a new value 
//        PrintStream console = System.out; 
//  
//        // Assign o to output stream 
//        System.setOut(o); 
//        System.out.println("This will be written to the text file"); 
//  
//        // Use stored value for output stream 
//        System.setOut(console); 
//        System.out.println("This will be written on the console!"); 
		
		
//		for (OWLAxiom ax : o.getLogicalAxioms()) {
//			if(ax instanceof OWLClassAssertionAxiom || ax instanceof OWLObjectPropertyAssertionAxiom || ax instanceof OWLNegativeObjectPropertyAssertionAxiom) {
//				System.out.println(ax);	
//			}
//		}
		
//		System.out.println("TBox: \n" + tBoxLoader.getTBox());
		
//		System.out.println(o);
//		
//
//		for (OWLAxiom ax : o.getLogicalAxioms()) {
//			if(ax.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
//				System.out.println(ax);
//				
//			}
//		}
		
		
//		for (OWLEntity e : o.getSignature()) {
//			if (!e.isBuiltIn() && e.getIRI().getFragment().startsWith("P")) {
//				System.out.println(e.getEntityType());
//			}
//			if (e.isBuiltIn()) {
//				System.out.println("isBuiltIn:" + e.getEntityType());
//			}
//		}
		
		
		
//		try {
//			o = man.createOntology();
//			System.out.println(o);
//		} catch (OWLOntologyCreationException e) {
//			e.printStackTrace();
//		}
		//System.out.println(man.getOntologies().size());
		
	
		
	//PROVA COSTRUTTI E TBOX 	
	/* 	
	Concept phD = new AtomicConcept("phD");
	Concept supervisor = new AtomicConcept("supervisor");
	Concept professor = new AtomicConcept("professor");
	
	System.out.println(phD);
	System.out.println(supervisor);
	System.out.println(professor);
	
	ArrayList<Concept> concepts1 = new ArrayList<Concept>();
	concepts1.add(phD);
	concepts1.add(professor);
	
	Concept c1 = new DisjunctionConcept(concepts1);
	System.out.println("c1: " + c1);
	
	ArrayList<Concept> concepts2 = new ArrayList<Concept>();
	concepts2.add(c1);
	concepts2.add(supervisor);
	Concept c2 = new ConjunctionConcept(concepts2);
	System.out.println("c2: " + c2);
	
	
	ArrayList<Concept> concepts = new ArrayList<Concept>();
	concepts.add(c1);
	concepts.add(c2);
	
	Concept neg = new NegatedConcept(professor);
	System.out.println("neg: " + neg);
	
	concepts.add(neg);
	Concept c = new DisjunctionConcept(concepts);
	System.out.println("c: " + c);
	
	Role r1 = new Role("r1");
	Role r2 = new Role("r2",true);
	Role r3 = new Role("r3");
	Role r4 = new Role("r4");
	
	Concept c3 = new ExistentialConcept(r1,c);
	System.out.println("c3: " + c3);
	
	Concept c4 = new UniversalConcept(r2,c3);
	System.out.println("c4: " + c4);
	
	Concept c5 = new MaxCardinalityConcept(2,r3,c4);
	System.out.println("c5: " + c5);
	
	Concept c6 = new MinCardinalityConcept(1,r4,c5);
	System.out.println("c6: " + c6);
	
	ConceptInclusionAxiom ci1 = new ConceptInclusionAxiom(c3, c6);
	ConceptInclusionAxiom ci2 = new ConceptInclusionAxiom(c4, c5);
	RoleInclusionAxiom ri1 = new RoleInclusionAxiom(r2, r1);
	RoleInclusionAxiom ri2 = new RoleInclusionAxiom(r3, r2);
	TransitivityAxiom tr1 = new TransitivityAxiom(r4);
	
	Set<ConceptInclusionAxiom> cis = new HashSet<ConceptInclusionAxiom>();
	Set<RoleInclusionAxiom> ris = new HashSet<RoleInclusionAxiom>();
	Set<TransitivityAxiom> trs = new HashSet<TransitivityAxiom>();
	cis.add(ci1);
	cis.add(ci2);
	ris.add(ri1);
	ris.add(ri2);
	trs.add(tr1);
	
	TBox tbox = new TBox(cis, ris, trs);
	
	System.out.println(tbox);
	*/
	
	
	}


}
