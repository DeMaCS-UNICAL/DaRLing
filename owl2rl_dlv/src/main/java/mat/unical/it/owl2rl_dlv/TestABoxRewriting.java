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
 ******************************************************************************/
package mat.unical.it.owl2rl_dlv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import datalog.costructs.Program;
import ontology.rewriter.ABoxLoader;
import ontology.rewriter.ABoxRewriter;

public class TestABoxRewriting {

	public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException {
		
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		
		PrintStream console = System.out;
		
		for (int i = 1; i <= 14; i++) {
			
			String fileName = "ABox_1_For_Query_";
			
			if (i < 10) {
				fileName += "0" + i;
			} else {
				fileName += "" + i;
			}
			File file = new File(fileName + ".owl");
			OWLOntology ont = man.loadOntologyFromOntologyDocument(file);
			
			ABoxLoader aboxLoader = new ABoxLoader();
			aboxLoader.loadABoxFrom(ont);
			
			ABoxRewriter aboxRewriter = new ABoxRewriter();
			Program facts = aboxRewriter.rewrite(aboxLoader.getAbox());
			
			PrintStream datalogABoxFile = new PrintStream(new File("datalog" + fileName + ".txt"));
			System.setOut(datalogABoxFile);
			System.out.println(facts);
			System.setOut(console);
			
		}
	}

}
