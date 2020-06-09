package darling.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import datalog.costructs.Program;
import datalog.costructs.Rule;

public class Manager {
	
	public static Boolean listFilesForFolder(File file, List<File> files) {
		if(!file.isDirectory()) {
			if(!file.exists()) {
	    		System.out.println("Error: File not found "+file);
	    		return false;
	    	}
			files.add(file);
			return true;
		}
	    for (final File fileEntry : file.listFiles()) {
	    	if(!fileEntry.exists()) {
	    		System.out.println("Error: File not found "+fileEntry);
	    		return false;
	    	}
	    	else if (fileEntry.isDirectory())
	            listFilesForFolder(fileEntry, files);
	        else
	        	files.add(fileEntry);
        }
	    return true;
	}
	
	public static void writeOnFile(String filename, Object obj, Boolean append) throws IOException {
	    FileWriter fileWriter = new FileWriter(filename, append);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(obj.toString());
	    printWriter.close();
	}
	
	public static void main(String[] args) {
		//TODO -a/--abox ABox -t/--tbox TBox -q/--query Query
		ABoxManager aboxManager = new ABoxManager();
		TBoxManager tboxManager = new TBoxManager();
		QueryManager queryManager = new QueryManager();
		
		int argAbox=0, argTBox=1, argQuery=2;
		
		List<File> aboxFiles = new ArrayList<File>();
		File abox=new File(args[argAbox]);
		if(listFilesForFolder(abox, aboxFiles)) {
			try {
				String fileNameWithoutExt = FilenameUtils.removeExtension(abox.getName());
				aboxManager.createProgramFrom(aboxFiles);
				Program aboxProgram=aboxManager.getProgram();
				writeOnFile(fileNameWithoutExt+".asp", aboxProgram, false);
			} catch (OWLOntologyCreationException e) {
				System.out.println("Error in ABox processing.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error when writing ABox in output.");
			}
		}
		
		List<File> tboxFiles = new ArrayList<File>();
		File tbox=new File(args[argTBox]);
		if(listFilesForFolder(tbox, tboxFiles)) {
			try {
				tboxManager.createProgramFrom(tboxFiles);
			} catch (OWLOntologyCreationException e) {
				System.out.println("Error in TBox processing.");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		File query=new File(args[argQuery]);
		try {
			queryManager.createDatalogQueriesFrom(query);
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found."+args[2]);
		}
		
		Program tboxProgram=tboxManager.getProgram();
		List<Rule> queries=queryManager.getPrograms();
		if(!queries.isEmpty()) {
			for(int i=0;i<queries.size();++i) {
				System.out.println("query"+i);
				String fileNameWithoutExt = FilenameUtils.removeExtension(tbox.getName());
				fileNameWithoutExt+="_query_"+(int)(i+1)+".asp";
				try {
					writeOnFile(fileNameWithoutExt, queries.get(i), false);
					writeOnFile(fileNameWithoutExt, tboxProgram, true);
				} catch (IOException e) {
					System.out.println("Error when writing query "+i+" in output.");
				}
			}
		}
		else {
			String fileNameWithoutExt = FilenameUtils.removeExtension(tbox.getName());
			try {
				writeOnFile(fileNameWithoutExt+".asp", tboxProgram, false);
			} catch (IOException e) {
				System.out.println("Error when writing TBox in output.");
			}
		}		
	}
}
