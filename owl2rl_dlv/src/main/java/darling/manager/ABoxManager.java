package darling.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import datalog.costructs.Program;
import datalog.costructs.Rule;
import ontology.rewriter.ABoxLoader;
import ontology.rewriter.ABoxRewriter;

public class ABoxManager {
	
Program datalogABox;
	
	public ABoxManager() {
		this.datalogABox = new Program();
	}
	
	public Program getProgram() {
		return this.datalogABox;
	}
	
	public void createProgramFrom(List<File> datasetFiles) throws OWLOntologyCreationException, FileNotFoundException {
		for (File file : datasetFiles) {
			
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLOntology ont_data = man.loadOntologyFromOntologyDocument(file);
			
			ABoxLoader aboxLoader = new ABoxLoader();
			aboxLoader.loadABoxFrom(ont_data);
			
			ABoxRewriter aboxRewriter = new ABoxRewriter();
			Program p = aboxRewriter.rewrite(aboxLoader.getAbox());
			
			for (Rule rule : p.getRules()) {
				datalogABox.getRules().add(rule);
			}
			
		}
	}

}
