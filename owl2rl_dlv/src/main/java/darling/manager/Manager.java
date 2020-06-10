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
package darling.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
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
        Option sameAsOption = new Option("s", "sameas", false, "Enable owl:sameAs management");
        sameAsOption.setRequired(false);
        options.addOption(sameAsOption);
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp(" ", options);
            System.exit(1);
        }
		       
        if(cmd.hasOption("help")) {
            formatter.printHelp(" ", options);
            System.exit(1);	
        }
		if(cmd.hasOption("abox")) {
			ABoxManager aboxManager = new ABoxManager();
			File abox=new File(cmd.getOptionValue("abox"));
			List<File> aboxFiles = new ArrayList<File>();
			if(listFilesForFolder(abox, aboxFiles)) {
				try {
					PrintStream original = System.out;
					String fileNameWithoutExt = FilenameUtils.removeExtension(abox.getName());
					System.setOut(original);
					aboxManager.createProgramFrom(aboxFiles);
					Program aboxProgram=aboxManager.getProgram();
					writeOnFile(fileNameWithoutExt+".asp", aboxProgram, false);
					System.out.println("ABox written in: "+fileNameWithoutExt+".asp");
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
					PrintStream original = System.out;
					tboxManager.createProgramFrom(tboxFiles);
					System.setOut(original);
					tboxProgram=tboxManager.getProgram();
					String fileNameWithoutExt = FilenameUtils.removeExtension(tbox.getName());
					writeOnFile(fileNameWithoutExt+".asp", tboxProgram, false);
					System.out.println("TBox written in: "+fileNameWithoutExt+".asp");
				} catch (OWLOntologyCreationException e) {
					System.out.println("Error in TBox processing.");
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(cmd.hasOption("query")) {
			QueryManager queryManager = new QueryManager();
			File query=new File(cmd.getOptionValue("query"));
			try {
				PrintStream original = System.out;
				queryManager.createDatalogQueriesFrom(query);
				System.setOut(original);
			} catch (FileNotFoundException e) {
				System.out.println("Error: File not found "+cmd.getOptionValue("query"));
			}
			List<Rule> queries=queryManager.getPrograms();
			if(!queries.isEmpty()) {
				for(int i=0;i<queries.size();++i) {
					String fileNameWithoutExt = FilenameUtils.removeExtension(tbox.getName());
					fileNameWithoutExt+="_query_"+(int)(i+1)+".asp";
					try {						
						if(tboxProgram!=null) 
							writeOnFile(fileNameWithoutExt, queries.get(i), false);
						writeOnFile(fileNameWithoutExt, tboxProgram, true);
						System.out.println("Query "+(int)(i+1)+" written in: "+fileNameWithoutExt);
					} catch (IOException e) {
						System.out.println("Error when writing query "+i+" in output.");
					}
				}
			}
		}
	}
}
