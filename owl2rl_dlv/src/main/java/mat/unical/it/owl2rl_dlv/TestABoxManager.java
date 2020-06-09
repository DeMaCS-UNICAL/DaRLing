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
