package query.translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import datalog.costructs.Rule;

public class QueryFileTranslator {
	
	private List<Rule> datalogQueries;
	private Scanner scanner;

	public QueryFileTranslator() {	
		datalogQueries = new ArrayList<Rule>();
	}
	
	public List<Rule> getDatalogQueries() {
		return datalogQueries;
	}

	public void parseFrom(File file) throws FileNotFoundException {
		QueryConstructor constructor = new QueryConstructor();
		
		scanner = new Scanner(file);
		
		String queryString = "";
		while (scanner.hasNextLine()) {
			String lineFile = scanner.nextLine();
			if (!(lineFile.isEmpty() || lineFile.startsWith("#"))) {
				if (!(lineFile.startsWith("[") && lineFile.endsWith("]"))) {
					queryString += lineFile + "\n";
				}
				else {
					if (!(queryString.isEmpty())) {
						Rule datalogQuery = constructor.translate(queryString);
						datalogQueries.add(datalogQuery);
					}
					queryString = "";
				}
			}
		}
		
		if (!(queryString.isEmpty())) {
			Rule datalogQuery = constructor.translate(queryString);
			datalogQueries.add(datalogQuery);
		}
		
	}
	

}
