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


package query.translator;

import java.util.*;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import datalog.costructs.*;

public class QueryConstructor {
	
	private static final String HEAD_PREDICATE = "ans";
	
	public QueryConstructor () {}
	
	public Rule translate (String queryString) {
		
		Query query = QueryFactory.create(queryString);
		Rule queryDatalog = new Rule();
		
		if (query.isSelectType()) {
			
			
			// Create head
			List<Var> headSPARQLVariables = query.getProjectVars();
			ArrayList<Term> headVariables = new ArrayList<Term>();
			for (Var x : headSPARQLVariables) {
				headVariables.add(new Variable(x.getVarName()));
			}
			Head queryHead = new Head(new Literal(HEAD_PREDICATE,headVariables));
			
			queryDatalog.setHead(queryHead);
			
			// Create body
			Body queryBody = new Body();
			
			Element queryPattern = query.getQueryPattern();
			
			if (queryPattern instanceof ElementGroup) {
				ElementGroup group = (ElementGroup) queryPattern;
				for (Element elem : group.getElements()) {
					if (elem instanceof ElementPathBlock) {
						ElementPathBlock block = (ElementPathBlock) elem;
						for (TriplePath triplePath : block.getPattern()) {
							Triple triple = triplePath.asTriple();
							if (triple.getPredicate().getURI().equals(OWLRDFVocabulary.RDF_TYPE.toString())) {
								Literal bodyLiteral = new Literal("<" + triple.getObject().toString() + ">",1);
								if (triple.getSubject().isVariable()) {
									Var x = (Var) triple.getSubject();
									bodyLiteral.getArguments().add(new Variable(x.getVarName()));
								}
								else {
									bodyLiteral.getArguments().add(new Constant(triple.getSubject().toString()));
								}
								queryBody.getBodyLiterals().add(bodyLiteral);
							}
							else {
								Literal bodyLiteral = new Literal("<" + triple.getPredicate().toString() + ">",2);
								
								if (triple.getSubject().isVariable()) {
									Var x = (Var) triple.getSubject();
									bodyLiteral.getArguments().add(new Variable(x.getVarName()));
								}
								else {
									bodyLiteral.getArguments().add(new Constant(triple.getSubject().toString()));
								}
								
								if (triple.getObject().isVariable()) {
									Var x = (Var) triple.getObject();
									bodyLiteral.getArguments().add(new Variable(x.getVarName()));
								}
								else {
									bodyLiteral.getArguments().add(new Constant(triple.getObject().toString()));
								}
								
								queryBody.getBodyLiterals().add(bodyLiteral);
								
							}
						}
					}
				}
				
			}
			
			queryDatalog.setHead(queryHead);
			queryDatalog.setBody(queryBody);
			
			
			
//			if (query.isDistinct()) {
//				//TODO 
//			}
			
		}
		return queryDatalog;
		
	}

}
