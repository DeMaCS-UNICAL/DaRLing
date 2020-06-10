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

import datalog.costructs.Constant;
import datalog.costructs.Head;
import datalog.costructs.Literal;
import datalog.costructs.Program;
import datalog.costructs.Rule;
import ontology.abox.ABox;
import ontology.abox.ConceptAssertion;
import ontology.abox.RoleAssertion;

public class ABoxRewriter {
	
	public ABoxRewriter() {	}
	
	public Program rewrite(ABox abox) {
		
		Program datalogRewritten = new Program();
		
		for (ConceptAssertion ca : abox.getConceptAssertions()) {
			Literal p = new Literal(ca.getConcept().toString(), 1);
			Constant a = new Constant(ca.getIndividual().toString()); 
			p.getArguments().add(a);
			Rule unaryFact = new Rule();
			unaryFact.setHead(new Head(p)); 
			datalogRewritten.getRules().add(unaryFact);
		}
		
		for (RoleAssertion ra : abox.getRoleAssertions()) {
			Literal r = new Literal(ra.getRole().toString(), 2);
			
			Constant a = new Constant(ra.getIndividual1().toString());
			r.getArguments().add(a);
			
			Constant b = new Constant(ra.getIndividual2().toString());
			if (ra.isIndividual2Integer()) {
				b.setInteger(true);
			}
			r.getArguments().add(b);
			
			Rule binaryFact = new Rule();
			binaryFact.setHead(new Head(r)); 
			datalogRewritten.getRules().add(binaryFact);
		}
		
		return datalogRewritten;
		
	}

}
