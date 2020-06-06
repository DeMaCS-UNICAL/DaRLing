package ontology.rewriter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;

import ontology.abox.*;
import ontology.constructs.*;


public class ABoxLoader {
	
	private ABox abox;

	public ABoxLoader() {
		abox = new ABox();
	}

	public ABox getAbox() {
		return abox;
	}

	public void loadABoxFrom(OWLOntology myOntology) {
		
		Set<ConceptAssertion> cas = abox.getConceptAssertions();
		Set<RoleAssertion> ras = abox.getRoleAssertions();
		
		Stream<OWLAxiom> streamAxs = myOntology.axioms();
		
		for (Iterator<OWLAxiom> iterator = streamAxs.iterator(); iterator.hasNext();) {
			
			OWLAxiom ax = iterator.next();
			
				// Class Assertions
				if (ax instanceof OWLClassAssertionAxiom) {
					
//					System.out.println("Processing Class Assertion Axiom: " + ax);
					
					OWLClassAssertionAxiom classAssertion = (OWLClassAssertionAxiom) ax;
					
					if (classAssertion.getClassExpression() instanceof OWLClass) {
						AtomicConcept concept = new AtomicConcept(((OWLClass) classAssertion.getClassExpression()).toStringID());
						Individual individual = new Individual(classAssertion.getIndividual().toStringID());
						ConceptAssertion ca = new ConceptAssertion(concept, individual);
						cas.add(ca);
					}else {
						System.out.println("Error during loading ABox: Concept not Atomic found in:" + ax);
					}
					
				}
				
				// Object Property Assertions
				else if (ax instanceof OWLObjectPropertyAssertionAxiom) {
					
//					System.out.println("Processing Object Property Assertion Axiom: " + ax);
					
					OWLObjectPropertyAssertionAxiom objPropertyAssertion = (OWLObjectPropertyAssertionAxiom) ax;
					if (objPropertyAssertion.getProperty() instanceof OWLProperty) {
						
						OWLProperty property = (OWLProperty) objPropertyAssertion.getProperty();
					
						Role role = new Role(property.toStringID());
						
						if (property instanceof OWLObjectInverseOf) {
							role.setInverse(true);
						}
						
						Individual source = new Individual(objPropertyAssertion.getSubject().toStringID());
						Individual target = new Individual(objPropertyAssertion.getObject().toStringID());
						
						ras.add(new RoleAssertion(role, source, target));
						
					}else {
						System.out.println("Error during loading ABox: Unmanaged role found in:" + ax);
					}
			
				}	
				
				// Data Property Assertions
				else if (ax instanceof OWLDataPropertyAssertionAxiom) {
					
//					System.out.println("Processing Data Property Assertion Axiom: " + ax);
					
					OWLDataPropertyAssertionAxiom dataPropertyAssertion = (OWLDataPropertyAssertionAxiom) ax;
					if (dataPropertyAssertion.getProperty() instanceof OWLProperty) {
						if (dataPropertyAssertion.getObject().getDatatype().isString() || dataPropertyAssertion.getObject().getDatatype().isInteger()) {
						
							OWLProperty property = (OWLProperty) dataPropertyAssertion.getProperty();
						
							Role role = new Role(property.toStringID());
							
							if (property instanceof OWLObjectInverseOf) {
								role.setInverse(true);
							}
							
							Individual source = new Individual(dataPropertyAssertion.getSubject().toStringID());
							Individual target = new Individual(dataPropertyAssertion.getObject().getLiteral().toString());
							
							RoleAssertion ra = new RoleAssertion(role, source, target);
							if (dataPropertyAssertion.getObject().getDatatype().isInteger()) {
								ra.setIndividual2Integer(true);
							}
							ras.add(ra);
							
						} else {
							System.out.println("Error during loading ABox: Unmanaged dataType found in:" + ax);
						}
						
					}else {
						System.out.println("Error during loading ABox: Unmanaged role found in:" + ax);
					}
					
				}

				// Declaration Axioms
				else if (ax instanceof OWLDeclarationAxiom) {}
				
			
			
				else {
					System.out.println("Error during loading ABox: unmanaged axiom: " + ax);
				}
			}
	
	}
	
	
	
	
	

	
}
