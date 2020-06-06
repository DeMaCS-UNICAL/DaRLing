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
