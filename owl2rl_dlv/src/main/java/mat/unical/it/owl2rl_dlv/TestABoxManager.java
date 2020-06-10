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
import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import darling.manager.ABoxManager;
import datalog.costructs.Program;

public class TestABoxManager {

	public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException {
		// TODO Auto-generated method stub
		
		ABoxManager aboxManager = new ABoxManager();
		ArrayList<File> files = new ArrayList<File>();
		File f1 = new File("ABox_1_For_Query_01.owl");
		File f2 = new File("ABox_1_For_Query_02.owl");
		File f3 = new File("ABox_1_For_Query_03.owl");
		File f4 = new File("ABox_1_For_Query_04.owl");
		files.add(f1);
		files.add(f2);
		files.add(f3);
		files.add(f4);
		aboxManager.createProgramFrom(files);
		Program p = aboxManager.getProgram();
		
		System.out.println(p);
		
	}

}
