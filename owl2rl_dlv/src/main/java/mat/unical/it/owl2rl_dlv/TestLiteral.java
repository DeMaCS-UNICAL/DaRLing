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


package mat.unical.it.owl2rl_dlv;

import datalog.costructs.Body;
import datalog.costructs.Constant;
import datalog.costructs.Head;
import datalog.costructs.Literal;
import datalog.costructs.NotEqualToLiteral;
import datalog.costructs.Program;
import datalog.costructs.Rule;
import datalog.costructs.Term;
import datalog.costructs.Variable;
import ontology.constructs.AtomicConcept;
import ontology.constructs.NegatedConcept;

public class TestLiteral {

	public static void main(String[] args) {
		
		Term t1 = new Constant("ciao");
		Variable t2 = new Variable("X");
		Variable t3 = new Variable("Y");
		
//		System.out.println("a(" + t1 + "," + t2 + ")");
		
		Literal l1 = new Literal("a",2);
		l1.getArguments().add(t1);
		l1.getArguments().add(t2);
		Literal l2 = new Literal("b",1);
		l2.getArguments().add(t3);
		Literal l3 = new Literal("c",3);
		l3.getArguments().add(t1);
		l3.getArguments().add(t2);
		l3.getArguments().add(t3);
		l3.setNegative(true);
		NotEqualToLiteral l4 = new NotEqualToLiteral(t2,t3);
		NotEqualToLiteral l5 = new NotEqualToLiteral(t2,t1);
		

		
		
		Body body = new Body();
		body.getBodyLiterals().add(l1);
		body.getBodyLiterals().add(l2);
		body.getBodyLiterals().add(l3);
		body.getBodyLiterals().add(l1);
		body.getInequalities().add(l4);
		
		
		Body body2 = new Body();
		body2.getBodyLiterals().add(l2);
		body2.getBodyLiterals().add(l3);
		body2.getInequalities().add(l4);
		body2.getInequalities().add(l5);
		
		Rule rule1 = new Rule(new Head(l1), body);
		System.out.println(rule1);
		Rule rule2 = new Rule(new Head(), body2);
		System.out.println(rule2);
		
		Program p = new Program();
		p.getRules().add(rule1);
		p.getRules().add(rule2);
		
		System.out.println("Program:\n" + p);
		
		AtomicConcept c = new AtomicConcept("concept");
		NegatedConcept nc = new NegatedConcept(c);
		System.out.println(c);
		System.out.println(c.getFreshAtomicConcept());
		System.out.println(nc);
		System.out.println(nc.getFreshAtomicConcept());
		System.out.println(nc.getStructuralTransformation());
		System.out.println(nc.getStructuralTransformation().getConcept());
		
		
	}

}
