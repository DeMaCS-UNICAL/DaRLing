package mat.unical.it.owl2rl_dlv;

import java.io.File;
import java.io.FileNotFoundException;

import datalog.costructs.Rule;
import query.translator.QueryFileTranslator;

public class TestQueryTranslator {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("LUBMqueries.SPARQL");
		
		QueryFileTranslator queriesTranslator = new QueryFileTranslator();
		queriesTranslator.parseFrom(file);

		for (Rule query : queriesTranslator.getDatalogQueries()) {
			System.out.println(query);
		}
		
	}

}
