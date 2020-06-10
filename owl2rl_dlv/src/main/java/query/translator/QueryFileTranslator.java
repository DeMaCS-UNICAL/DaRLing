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
