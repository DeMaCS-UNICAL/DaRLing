package darling.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import datalog.costructs.Rule;
import query.translator.QueryFileTranslator;

public class QueryManager {
	
	private List<Rule> datalogQueries;
	
	public QueryManager() {
		datalogQueries = new ArrayList<Rule>();
	}
	
	public List<Rule> getPrograms() {
		return datalogQueries;
	}
	
	public void createDatalogQueriesFrom(File file) throws FileNotFoundException {
		QueryFileTranslator queriesTranslator = new QueryFileTranslator();
		queriesTranslator.parseFrom(file);
		datalogQueries = queriesTranslator.getDatalogQueries();  
	}

}
