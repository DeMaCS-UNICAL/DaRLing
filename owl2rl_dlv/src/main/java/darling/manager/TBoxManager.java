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
import ontology.rewriter.OWL2RL_TBoxRewriter;
import ontology.rewriter.TBoxLoader;

public class TBoxManager {
	
	Program datalogTBox;
	
	public TBoxManager() {
		this.datalogTBox = new Program();
	}
	
	public Program getProgram() {
		return this.datalogTBox;
	}
	
	public void createProgramFrom(List<File> ontologyFiles) throws OWLOntologyCreationException, FileNotFoundException {
		for (File file : ontologyFiles) {
			
			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLOntology ont = man.loadOntologyFromOntologyDocument(file);
		
			TBoxLoader tBoxLoader = new TBoxLoader(false);
			tBoxLoader.load(ont);
			
			OWL2RL_TBoxRewriter rewriter = new OWL2RL_TBoxRewriter();
			rewriter.rewrite(tBoxLoader.getTBox());
			Program p = rewriter.getDatalogProgram();
			
			for (Rule rule : p.getRules()) {
				if (rule.getHead().getHeadAtom().getPredicateName() != "") {   // does not consider constraints
					datalogTBox.getRules().add(rule);
				}
			}
			
		}
	}

}
