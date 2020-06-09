package darling.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		Options options = new Options();
        Option help = new Option("h", "help", false, "Print usage and exit");
        help.setRequired(false);
        options.addOption(help);
        Option aboxOption = new Option("a", "abox", true, "ABox input file(s)");
        aboxOption.setRequired(false);
        options.addOption(aboxOption);
        Option tboxOption = new Option("t", "tbox", true, "TBox input file(s)");
        tboxOption.setRequired(false);
        options.addOption(tboxOption);
        Option queryOption = new Option("q", "query", true, "Query input file(s)");
        queryOption.setRequired(false);
        options.addOption(queryOption);
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("java -jar darling.jar", options);
            System.exit(1);
        }
		       
        if(cmd.hasOption("help")) {
            formatter.printHelp("java -jar darling.jar", options);
            System.exit(1);	
        }
		if(cmd.hasOption("abox")) {
			ABoxManager aboxManager = new ABoxManager();
			File abox=new File(cmd.getOptionValue("abox"));
			List<File> aboxFiles = new ArrayList<File>();
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
		}
		
        Program tboxProgram = null;
        File tbox= null;
        
		if(cmd.hasOption("tbox")) {
			TBoxManager tboxManager = new TBoxManager();
			List<File> tboxFiles = new ArrayList<File>();
			tbox=new File(cmd.getOptionValue("tbox"));
			if(listFilesForFolder(tbox, tboxFiles)) {
				try {
					tboxManager.createProgramFrom(tboxFiles);
					tboxProgram=tboxManager.getProgram();
					String fileNameWithoutExt = FilenameUtils.removeExtension(tbox.getName());
					writeOnFile(fileNameWithoutExt+".asp", tboxProgram, false);
				} catch (OWLOntologyCreationException e) {
					System.out.println("Error in TBox processing.");
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(cmd.hasOption("query")) {
			QueryManager queryManager = new QueryManager();
			File query=new File(cmd.getOptionValue("query"));
			try {
				queryManager.createDatalogQueriesFrom(query);
			} catch (FileNotFoundException e) {
				System.out.println("Error: File not found."+args[2]);
			}
			List<Rule> queries=queryManager.getPrograms();
			if(!queries.isEmpty()) {
				for(int i=0;i<queries.size();++i) {
					System.out.println("query"+i);
					String fileNameWithoutExt = FilenameUtils.removeExtension(tbox.getName());
					fileNameWithoutExt+="_query_"+(int)(i+1)+".asp";
					try {
						if(tboxProgram!=null) 
							writeOnFile(fileNameWithoutExt, queries.get(i), false);
						writeOnFile(fileNameWithoutExt, tboxProgram, true);
					} catch (IOException e) {
						System.out.println("Error when writing query "+i+" in output.");
					}
				}
			}
		}
	}
}
