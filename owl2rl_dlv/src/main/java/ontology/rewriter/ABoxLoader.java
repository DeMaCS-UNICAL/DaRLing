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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

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
						AtomicConcept concept = new AtomicConcept(((OWLClass) classAssertion.getClassExpression()).toString());
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
					
						Role role = new Role(property.toString());
						
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
						
							Role role = new Role(property.toString());
							
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

				// Individual Equality (sameAs) Axioms
				else if (ax instanceof OWLSameIndividualAxiom) {
					OWLSameIndividualAxiom owlSameAsAxiom = (OWLSameIndividualAxiom) ax;
					Stream<OWLIndividual> streamIndividuals = owlSameAsAxiom.individuals();
					
					Iterator<OWLIndividual> indivIterator = streamIndividuals.iterator();
					Individual firstIndividual = new Individual(indivIterator.next().toStringID());
					
					Role sameAsRole = new Role("sameAs");
					
					while (indivIterator.hasNext()) {
						Individual currentIndividual = new Individual(indivIterator.next().toStringID());
						RoleAssertion sameAsAssertion = new RoleAssertion(sameAsRole, firstIndividual, currentIndividual);
						ras.add(sameAsAssertion);
					}
					
				}
				
			
			
				else {
					System.out.println("Error during loading ABox: unmanaged axiom: " + ax);
				}
			}
	
	}
	
	
	
	
	

	
}
