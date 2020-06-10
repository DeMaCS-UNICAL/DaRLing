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
