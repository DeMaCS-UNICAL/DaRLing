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

import ontology.abox.ABox;
import ontology.abox.ConceptAssertion;
import ontology.abox.RoleAssertion;
import ontology.constructs.*;
import ontology.tbox.*;

import java.util.*;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;
//import org.semanticweb.owlapi.utilities.Injector;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Loader {
//	private static final Logger logger = Logger.getLogger(TBoxLoader.class);
	
	private boolean shortForm;
	
	private TBox tbox;
	private ABox abox;
	private Set<UnmanagedAxiom> unmanagedAxioms = new HashSet<UnmanagedAxiom>();
	
	public Loader() { 
		tbox = new TBox();
		abox = new ABox();
		shortForm = false;
	
		// Logger Configuration
//		BasicConfigurator.configure();
//		logger.setLevel(Level.ERROR);
	}
	
	public Loader(boolean shortForm) { 
		tbox = new TBox();
		abox = new ABox();
		this.shortForm = shortForm;
		
		// Logger Configuration
//		BasicConfigurator.configure();
//		logger.setLevel(Level.ERROR);
	}
	
	public TBox getTBox() {
		return tbox;
	}
	
	public ABox getABox() {
		return abox;
	}
	
	public Set<UnmanagedAxiom> getUnmanagedAxioms() {
		return unmanagedAxioms;
	}

	public void load(OWLOntology myOntology) {
		
		// Logical axioms
		Set<ConceptInclusionAxiom> cis = tbox.getConceptInclusions();
		Set<RoleInclusionAxiom> ris = tbox.getRoleInclusions();
		Set<TransitivityAxiom> trs = tbox.getTransitivityAxioms();
		Set<DisjointRolesAxiom> drs = tbox.getDisjointRolesAxioms();		
		Set<IrreflexiveAxiom> irrefs = tbox.getIrreflexiveAxioms();
		Set<AsymmetricAxiom> asys = tbox.getAsymmetricAxioms();
		// Assertions
		Set<ConceptAssertion> cas = abox.getConceptAssertions();
		Set<RoleAssertion> ras = abox.getRoleAssertions();
		
		/* DISALLOWED
		Set<ReflexiveAxiom> refs = tbox.getReflexiveAxioms();
		*/
		 
		Stream<OWLAxiom> streamAxs = myOntology.axioms();
		
		for (Iterator<OWLAxiom> iterator = streamAxs.iterator(); iterator.hasNext();) {
			
			OWLAxiom ax = iterator.next();
			
			
			// TBox loading
			
			if (ax instanceof OWLLogicalAxiom) {
				
				System.out.println("Read axiom: " + ax);
				
				// ClassAxioms
				if (ax instanceof OWLClassAxiom) {	
				
					
					// SubClassOf
					if (ax instanceof OWLSubClassOfAxiom) {
						OWLSubClassOfAxiom subClassAx = (OWLSubClassOfAxiom) ax;
						Concept subConcept = generateConceptFrom(subClassAx.getSubClass());
						Concept superConcept = generateConceptFrom(subClassAx.getSuperClass());
						if (subConcept.isManaged() && superConcept.isManaged()) {
							ConceptInclusionAxiom ci = new ConceptInclusionAxiom(subConcept,superConcept);
							cis.add(ci);
							System.out.println("Added axiom: " + ci);
						} else {
							unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
							System.out.println("Some Unmanaged Concept in Axiom: " + ax);
						}
					}
					// EquivalentClasses
					else if (ax instanceof OWLEquivalentClassesAxiom) {
						OWLEquivalentClassesAxiom equivClassAx = (OWLEquivalentClassesAxiom) ax;
						
						Set<OWLClassExpression> exprs = equivClassAx.getClassExpressionsMinus();
						for(OWLClassExpression subExpr : exprs) {
							Concept subConcept = generateConceptFrom(subExpr);
							if (subConcept.isManaged()) {
								for (OWLClassExpression superExp : exprs) {
									Concept superConcept = generateConceptFrom(superExp);
									if (superConcept.isManaged()) {
										if (!subExpr.equals(superExp)) {
											ConceptInclusionAxiom ci = new ConceptInclusionAxiom(subConcept,superConcept);
											cis.add(ci);
											System.out.println("Added axiom: " + ci);
										}
									} else {
										unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
										System.out.println("Some Unmanaged Concept in Axiom: " + ax);
									}
								}
							} else {
								unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
								System.out.println("Some Unmanaged Concept in Axiom: " + ax);
							}
						}
					}
					// DisjointClasses
					else if (ax instanceof OWLDisjointClassesAxiom) {
						OWLDisjointClassesAxiom disjClassesAx =(OWLDisjointClassesAxiom) ax;
						Set<OWLClassExpression> exprs = disjClassesAx.getClassExpressionsMinus();
						List<Concept> concepts = new ArrayList<Concept>();
						for (OWLClassExpression expr : exprs) {
							concepts.add(generateConceptFrom(expr));
						}
						for (int i = 0; i < concepts.size()-1; i++) {
							for (int j = i+1; j < concepts.size();j++) {
								Concept concept1 = concepts.get(i);
								Concept concept2 = concepts.get(j);
								if (concept1.isManaged() && concept2.isManaged()) {
									ConjunctionConcept andConcept = new ConjunctionConcept(new LinkedList<Concept>());
									andConcept.getConcepts().add(concept1);
									andConcept.getConcepts().add(concept2);
									ConceptInclusionAxiom ci = new ConceptInclusionAxiom(andConcept,new BottomConcept());
									cis.add(ci);
									System.out.println("Added axiom: " + ci);
								} else {
									unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
									System.out.println("Some Unmanaged Concept in Axiom: " + ax);
								}
							}
						}
					}
					
					else {
						unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
						System.out.println("Unmanaged Axiom");
					}
					
				}
				
				
				// ObjectPropertyAxioms
				else if (ax instanceof OWLObjectPropertyAxiom) {
					
					
					// SubObjectPropertyOf
					if (ax instanceof OWLSubObjectPropertyOfAxiom) {
						OWLSubObjectPropertyOfAxiom subProp = (OWLSubObjectPropertyOfAxiom) ax;
						Role subRole = generateRoleFrom(subProp.getSubProperty());
						Role superRole = generateRoleFrom(subProp.getSuperProperty());
						RoleInclusionAxiom ri = new RoleInclusionAxiom(subRole,superRole);
						ris.add(ri);
						System.out.println("Added axiom: " + ri);
					}
					// EquivalentObjectProperties
					else if (ax instanceof OWLEquivalentObjectPropertiesAxiom) {
						OWLEquivalentObjectPropertiesAxiom equivProp = (OWLEquivalentObjectPropertiesAxiom) ax;
						Set<OWLObjectPropertyExpression> propExprs = equivProp.getPropertiesMinus(null);
						for (OWLObjectPropertyExpression subProp : propExprs) {
							Role subRole = generateRoleFrom(subProp);
							for (OWLObjectPropertyExpression superProp : propExprs) {
								Role superRole = generateRoleFrom(superProp);
								if(!subProp.equals(superProp)) {
									RoleInclusionAxiom ri = new RoleInclusionAxiom(subRole,superRole);
									ris.add(ri);
									System.out.println("Added axiom: " + ri);
								}
							}
						}
					}
					// DisjointObjectProperties
					else if (ax instanceof OWLDisjointObjectPropertiesAxiom) {
						OWLDisjointObjectPropertiesAxiom disjPropAx = (OWLDisjointObjectPropertiesAxiom) ax;
						Collection<OWLDisjointObjectPropertiesAxiom> disPropAxs = disjPropAx.asPairwiseAxioms();
						for (OWLDisjointObjectPropertiesAxiom disPropTwoAxs : disPropAxs) {
							Stream<OWLObjectPropertyExpression> properties = disPropTwoAxs.properties();
							Iterator<OWLObjectPropertyExpression> it = properties.iterator();
							Role role1 = generateRoleFrom(it.next());
							Role role2 = generateRoleFrom(it.next());
							DisjointRolesAxiom dr = new DisjointRolesAxiom(role1,role2);
							drs.add(dr);
							System.out.println("Added axiom: " + dr);
						}
					}
					// InverseObjectProperties
					else if (ax instanceof OWLInverseObjectPropertiesAxiom) {
						OWLInverseObjectPropertiesAxiom invPropAx = (OWLInverseObjectPropertiesAxiom) ax;
						Role firstRole = generateRoleFrom(invPropAx.getFirstProperty());
						Role secondRole = generateRoleFrom(invPropAx.getSecondProperty());
						RoleInclusionAxiom ri1 = new RoleInclusionAxiom(firstRole,secondRole.getInverseRole());
						ris.add(ri1);
						System.out.println("Added axiom: " + ri1);
						RoleInclusionAxiom ri2 = new RoleInclusionAxiom(secondRole.getInverseRole(),firstRole);
						ris.add(ri2);
						System.out.println("Added axiom: " + ri2);
						
					}
					// ObjectPropertyDomain
					else if (ax instanceof OWLObjectPropertyDomainAxiom) {
						OWLObjectPropertyDomainAxiom objPropDom = (OWLObjectPropertyDomainAxiom) ax;
						Concept concept = generateConceptFrom(objPropDom.getDomain());
						if (concept.isManaged()) {
							Role role = generateRoleFrom(objPropDom.getProperty());
							ConceptInclusionAxiom ci = new ConceptInclusionAxiom(new ExistentialConcept(role,new TopConcept()),concept);
							cis.add(ci);
							System.out.println("Added axiom: " + ci);
						} else {
							unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
							System.out.println("Some Unmanaged Concept in Axiom: " + ax);
						}
					}
					// ObjectPropertyRange
					else if (ax instanceof OWLObjectPropertyRangeAxiom) {
						OWLObjectPropertyRangeAxiom objPropRange = (OWLObjectPropertyRangeAxiom) ax;
						Concept concept = generateConceptFrom(objPropRange.getRange());
						if (concept.isManaged()) {
							Role role = generateRoleFrom(objPropRange.getProperty());
							ConceptInclusionAxiom ci = new ConceptInclusionAxiom(new TopConcept(),new UniversalConcept(role,concept));
							cis.add(ci);
							System.out.println("Added axiom: " + ci);
						} else {
							unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
							System.out.println("Some Unmanaged Concept in Axiom: " + ax);
						}
					}
					// FunctionalObjectProperty
					else if (ax instanceof OWLFunctionalObjectPropertyAxiom) {
						OWLFunctionalObjectPropertyAxiom funObjPropAx = (OWLFunctionalObjectPropertyAxiom) ax;
						Role role = generateRoleFrom(funObjPropAx.getProperty());
						ConceptInclusionAxiom ci = new ConceptInclusionAxiom(new TopConcept(),new MaxCardinalityConcept(1,role,new TopConcept()));
						cis.add(ci);
						System.out.println("Added axiom: " + ci);
					}
					// InverseFunctionalObjectProperty
					else if (ax instanceof OWLInverseFunctionalObjectPropertyAxiom) {
						OWLInverseFunctionalObjectPropertyAxiom invFunObjPropAx = (OWLInverseFunctionalObjectPropertyAxiom) ax;
						Role role = generateRoleFrom(invFunObjPropAx.getProperty());
						ConceptInclusionAxiom ci = new ConceptInclusionAxiom(new TopConcept(),new MaxCardinalityConcept(1,role.getInverseRole(),new TopConcept()));
						cis.add(ci);
						System.out.println("Added axiom: " + ci);
					}
					
					// IrreflexiveObjectProperty
					else if (ax instanceof OWLIrreflexiveObjectPropertyAxiom) {
						OWLIrreflexiveObjectPropertyAxiom irreflAx = (OWLIrreflexiveObjectPropertyAxiom) ax;
						Role role = generateRoleFrom(irreflAx.getProperty());
						IrreflexiveAxiom irref = new IrreflexiveAxiom(role);
						irrefs.add(irref);
						System.out.println("Added axiom: " + irref);
					}
					// SymmetricObjectProperty
					else if (ax instanceof OWLSymmetricObjectPropertyAxiom) {
						OWLSymmetricObjectPropertyAxiom symmetricProp = (OWLSymmetricObjectPropertyAxiom) ax;
						Role role = generateRoleFrom(symmetricProp.getProperty());
						RoleInclusionAxiom ri = new RoleInclusionAxiom(role,role.getInverseRole());
						ris.add(ri);
						System.out.println("Added axiom: " + ri);
					}
					// AsymmetricObjectProperty
					else if (ax instanceof OWLAsymmetricObjectPropertyAxiom) {
						OWLAsymmetricObjectPropertyAxiom asymmetricProp = (OWLAsymmetricObjectPropertyAxiom) ax;
						Role role = generateRoleFrom(asymmetricProp.getProperty());
						AsymmetricAxiom asy = new AsymmetricAxiom(role);
						asys.add(asy);
						System.out.println("Added axiom: " + asy);
					}
					// TransitiveObjectProperty
					else if (ax instanceof OWLTransitiveObjectPropertyAxiom) {
						OWLTransitiveObjectPropertyAxiom transProp = (OWLTransitiveObjectPropertyAxiom) ax;
						Role role = generateRoleFrom(transProp.getProperty());
						TransitivityAxiom tr = new TransitivityAxiom(role);
						trs.add(tr);
						System.out.println("Added axiom: " + tr);
					} 
					else {
						unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
						System.out.println("Unmanaged Axiom");
					}
					
					
				}
				
				// DataPropertyAxioms
				else if (ax instanceof OWLDataPropertyAxiom) {
					
					// SubDataPropertyOf
					if (ax instanceof OWLSubDataPropertyOfAxiom) {
						OWLSubDataPropertyOfAxiom subProp = (OWLSubDataPropertyOfAxiom) ax;
						Role subRole = generateRoleFrom(subProp.getSubProperty());
						Role superRole = generateRoleFrom(subProp.getSuperProperty());
						RoleInclusionAxiom ri = new RoleInclusionAxiom(subRole,superRole);
						ris.add(ri);
						System.out.println("Added axiom: " + ri);
					}
					// EquivalentDataProperties
					else if (ax instanceof OWLEquivalentDataPropertiesAxiom) {
						OWLEquivalentDataPropertiesAxiom equivProp = (OWLEquivalentDataPropertiesAxiom) ax;
						Set<OWLDataPropertyExpression> propExprs = equivProp.getPropertiesMinus(null);
						for (OWLDataPropertyExpression subProp : propExprs) {
							Role subRole = generateRoleFrom(subProp);
							for (OWLDataPropertyExpression superProp : propExprs) {
								Role superRole = generateRoleFrom(superProp);
								if(!subProp.equals(superProp)) {
									RoleInclusionAxiom ri = new RoleInclusionAxiom(subRole,superRole);
									ris.add(ri);
									System.out.println("Added axiom: " + ri);
								}
							}
						}
					}
					// DisjointDataProperties
					else if (ax instanceof OWLDisjointDataPropertiesAxiom) {
						OWLDisjointDataPropertiesAxiom disjPropAx = (OWLDisjointDataPropertiesAxiom) ax;
						Collection<OWLDisjointDataPropertiesAxiom> disPropAxs = disjPropAx.asPairwiseAxioms();
						for (OWLDisjointDataPropertiesAxiom disPropTwoAxs : disPropAxs) {
							Stream<OWLDataPropertyExpression> properties = disPropTwoAxs.properties();
							Iterator<OWLDataPropertyExpression> it = properties.iterator();
							Role role1 = generateRoleFrom(it.next());
							Role role2 = generateRoleFrom(it.next());
							DisjointRolesAxiom dr = new DisjointRolesAxiom(role1,role2);
							drs.add(dr);
							System.out.println("Added axiom: " + dr);
						}
					}
					// DataPropertyDomain
					else if (ax instanceof OWLDataPropertyDomainAxiom) {
						OWLDataPropertyDomainAxiom dataPropDom = (OWLDataPropertyDomainAxiom) ax;
						Concept concept = generateConceptFrom(dataPropDom.getDomain());
						if (concept.isManaged()) {
							Role role = generateRoleFrom(dataPropDom.getProperty());
							ConceptInclusionAxiom ci = new ConceptInclusionAxiom(new ExistentialConcept(role,new TopConcept()),concept);
							cis.add(ci);
							System.out.println("Added axiom: " + ci);
						} else {
							unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
							System.out.println("Some Unmanaged Concept in Axiom: " + ax);
						}
					}
					
					// FunctionalDataProperty
					else if (ax instanceof OWLFunctionalDataPropertyAxiom) {
						OWLFunctionalDataPropertyAxiom funDataPropAx = (OWLFunctionalDataPropertyAxiom) ax;
						Role role = generateRoleFrom(funDataPropAx.getProperty());
						ConceptInclusionAxiom ci = new ConceptInclusionAxiom(new TopConcept(),new MaxCardinalityConcept(1,role,new TopConcept()));
						cis.add(ci);
						System.out.println("Added axiom: " + ci);
					}
					
					else {
						unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
						System.out.println("Unmanaged Axiom");
					}
					
				}
				
				/*
				 - Unmanaged Axioms:
					- DataPropertyRangeAxiom (Section 9.3.5)
					- DataTypeDefinition (Section 9.4)
					- Keys (Section 9.5)
					- Assertions
				*/
				else {
					unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
					System.out.println("Unmanaged Axiom");
				}
				
			}
			
			// ABox loading
			
			// Class Assertions
			else if (ax instanceof OWLClassAssertionAxiom) {
				
				OWLClassAssertionAxiom classAssertion = (OWLClassAssertionAxiom) ax;
				
				
				// Atomic concept assertion 
				if (classAssertion.getClassExpression() instanceof OWLClass) {
					AtomicConcept concept = new AtomicConcept(((OWLClass) classAssertion.getClassExpression()).toString());
					Individual individual = new Individual(classAssertion.getIndividual().toStringID());
					ConceptAssertion ca = new ConceptAssertion(concept, individual);
					cas.add(ca);
				}
				
				// Complex concept assertion (C(a) --> {D(a) and D 'subConceptOf' C, with D a fresh atomic concept uniquely associated to C})
				else {
					
					AtomicConcept concept = new AtomicConcept("Atomic_" + classAssertion.getClassExpression().toString());
					Individual individual = new Individual(classAssertion.getIndividual().toStringID());
					ConceptAssertion ca = new ConceptAssertion(concept, individual);
					cas.add(ca);
					//
					Concept superConcept = generateConceptFrom(classAssertion.getClassExpression());
					if (concept.isManaged() && superConcept.isManaged()) {
						ConceptInclusionAxiom ci = new ConceptInclusionAxiom(concept,superConcept);
						cis.add(ci);
						System.out.println("Added axiom: " + ci);
					} else {
						unmanagedAxioms.add(new UnmanagedAxiom(ax.toString()));
						System.out.println("Unmanaged Axiom: " + ax);
					}

				}
				
			}
			
			// Object Property Assertions
			else if (ax instanceof OWLObjectPropertyAssertionAxiom) {
				
//				System.out.println("Processing Object Property Assertion Axiom: " + ax);
				
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
				
//				System.out.println("Processing Data Property Assertion Axiom: " + ax);
				
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
	
	
	private Concept generateConceptFrom(OWLClassExpression expr) {
		
		// TopEntity -> TopConcept
		if(expr.isTopEntity()) {
			return new TopConcept();
		}
		// BottomEntity -> BottomConcept
		else if(expr.isBottomEntity()) {
			return new BottomConcept();
		}
		// Class -> AtomicConcept
		else if(expr instanceof OWLClass) {
			if (shortForm) {
				return new AtomicConcept(((OWLClass) expr).getIRI().getShortForm());
			} else {
//				return new AtomicConcept(((OWLClass) expr).toStringID());
				return new AtomicConcept(((OWLClass) expr).toString());
			}
		}
		// ObjectIntersectionOf -> ConjunctionConcept
		else if (expr instanceof OWLObjectIntersectionOf) {
			OWLObjectIntersectionOf intersection = (OWLObjectIntersectionOf) expr;
			Set<OWLClassExpression> exprs = (intersection.asConjunctSet());
			List<Concept> concepts = new ArrayList<Concept>();
			for (OWLClassExpression newexpr : exprs) {
				Concept c = generateConceptFrom(newexpr);
				concepts.add(c);
			}
			return new ConjunctionConcept(concepts);
		}
		// ObjectUnionOf -> DisjunctionConcept
		else if (expr instanceof OWLObjectUnionOf) {
			OWLObjectUnionOf union = (OWLObjectUnionOf) expr;
			Set<OWLClassExpression> exprs = (union.asDisjunctSet());
			List<Concept> concepts = new ArrayList<Concept>();
			for (OWLClassExpression newexpr : exprs) {
				Concept c = generateConceptFrom(newexpr);
				concepts.add(c);
			}
			return new DisjunctionConcept(concepts);
		}
		// ObjectComplementOf -> NegatedConcept
		else if(expr instanceof OWLObjectComplementOf) {
			OWLObjectComplementOf negExpr = (OWLObjectComplementOf) expr;
			Concept concept = generateConceptFrom(negExpr.getOperand()); //TODO: .getObjectComplementOf() instead of .getOperand()?
			return new NegatedConcept(concept);
		}
		// ObjectSomeValuesFrom -> ExistentialConcept
		else if (expr instanceof OWLObjectSomeValuesFrom) {
			OWLObjectSomeValuesFrom existentialExpr = (OWLObjectSomeValuesFrom) expr;
			Role role = generateRoleFrom(existentialExpr.getProperty());
			Concept concept = generateConceptFrom(existentialExpr.getFiller());  
			return new ExistentialConcept(role,concept);
		}
		// ObjectAllValuesFrom -> UniversalConcept
		else if (expr instanceof OWLObjectAllValuesFrom) {
			OWLObjectAllValuesFrom universalExpr = (OWLObjectAllValuesFrom) expr;
			Role role = generateRoleFrom(universalExpr.getProperty());
			Concept concept = generateConceptFrom(universalExpr.getFiller()); 
			return new UniversalConcept(role,concept);
		}
		// ObjectMinCardinality -> MinCardinalityConcept
		else if (expr instanceof OWLObjectMinCardinality) {
			OWLObjectMinCardinality minCardinalityExpr = (OWLObjectMinCardinality) expr;
			int n = minCardinalityExpr.getCardinality();
			Role role = generateRoleFrom(minCardinalityExpr.getProperty());
			Concept concept = generateConceptFrom(minCardinalityExpr.getFiller()); 
			return new MinCardinalityConcept(n,role,concept);
		}
		// ObjectMaxCardinality -> MaxCardinalityConcept
		else if (expr instanceof OWLObjectMaxCardinality) {
			OWLObjectMaxCardinality maxCardinalityExpr = (OWLObjectMaxCardinality) expr;
			int n = maxCardinalityExpr.getCardinality();
			Role role = generateRoleFrom(maxCardinalityExpr.getProperty());
			Concept concept = generateConceptFrom(maxCardinalityExpr.getFiller()); 
			return new MaxCardinalityConcept(n,role,concept);
		}
		// ObjectExactCardinality -> MinCardinalityConcept, MaxCardinalityConcept 
		else if (expr instanceof OWLObjectExactCardinality) {
			OWLObjectExactCardinality exactCardExpr = (OWLObjectExactCardinality) expr;
			int n = exactCardExpr.getCardinality();
			Role role = generateRoleFrom(exactCardExpr.getProperty());
			Concept concept = generateConceptFrom(exactCardExpr.getFiller()); 
			Concept minCardConcept = new MinCardinalityConcept(n,role,concept);
			Concept maxCardConcept = new MaxCardinalityConcept(n,role,concept);
			List<Concept> minAndMaxConcepts = new ArrayList<Concept>();
			minAndMaxConcepts.add(maxCardConcept);
			minAndMaxConcepts.add(minCardConcept);
		    return new DisjunctionConcept(minAndMaxConcepts);
		}
		
		return new UnmanagedConcept();		// TODO throw some exception
		
		
		/*
		 - Unmanaged Class Expressions:
		 	- PropositionalConnectivesAndEnumerationOfIndividuals (Section 8.1):
	 			EnumerationOfIndividuals (Subsection 8.1.4)
			- ObjectPropertyRestrictions (Section 8.2):
				IndividualValueRestriction (Subsection 8.2.3)
				SelfRestriction (8.2.4)
			- DataPropertyRestrictions (Section 8.4)
		*/
		
	}
	
	private Role generateRoleFrom(OWLObjectPropertyExpression expr) {
		if(expr instanceof OWLProperty) {
			OWLProperty property = (OWLProperty) expr;
			if (shortForm) {
				if (property instanceof OWLObjectInverseOf) 
					return new Role(property.getIRI().getShortForm(),true);
				return new Role(property.getIRI().getShortForm());
			} else {
				if (property instanceof OWLObjectInverseOf) 
//					return new Role(property.toStringID(),true);
					return new Role(property.toString(),true);
//				return new Role(property.toStringID());
				return new Role(property.toString());
			}
		}
		return new UnmanagedRole();
	}
	
	private Role generateRoleFrom(OWLDataPropertyExpression expr) {
		if(expr instanceof OWLProperty) {
			OWLProperty property = (OWLProperty) expr;
			if (shortForm) {
				if (property instanceof OWLObjectInverseOf) 
					return new Role(property.getIRI().getShortForm(),true);
				return new Role(property.getIRI().getShortForm());
			} else {
				if (property instanceof OWLObjectInverseOf) 
					return new Role(property.toStringID(),true);
//					return new Role(property.toString(),true);
				return new Role(property.toStringID());
//				return new Role(property.toString());
			}
		}
		return new UnmanagedRole();
	}
	
	
}
