package mat.unical.it.owl2rl_dlv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import datalog.costructs.Rule;
import query.translator.QueryConstructor;

public class TestQueryConstructor {

	public static void main(String[] args) throws FileNotFoundException {
		
		PrintStream console = System.out;
		
		String query1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y	\n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:GraduateStudent .\n" + 
				"  ?X ub:takesCourse ?Y}";
		
		QueryConstructor qt1 = new QueryConstructor();
		Rule r1 = qt1.translate(query1);
		PrintStream query1File = new PrintStream(new File("datalog_Query_01.txt"));
		System.setOut(query1File);
		System.out.println(r1);
		System.setOut(console);
		
		String query2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y ?Z\n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:GraduateStudent .\n" + 
				"  ?Y rdf:type ub:University .\n" + 
				"  ?Z rdf:type ub:Department .\n" + 
				"  ?X ub:memberOf ?Z .\n" + 
				"  ?Z ub:subOrganizationOf ?Y .\n" + 
				"  ?X ub:undergraduateDegreeFrom ?Y}";
		
		QueryConstructor qt2 = new QueryConstructor();
		Rule r2 = qt2.translate(query2);
		PrintStream query2File = new PrintStream(new File("datalog_Query_02.txt"));
		System.setOut(query2File);
		System.out.println(r2);
		System.setOut(console);
		
		String query3 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y \n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Publication .\n" + 
				"  ?X ub:publicationAuthor \n" + 
				"        ?Y}";
		
		QueryConstructor qt3 = new QueryConstructor();
		Rule r3 = qt3.translate(query3);
		PrintStream query3File = new PrintStream(new File("datalog_Query_03.txt"));
		System.setOut(query3File);
		System.out.println(r3);
		System.setOut(console);
		
		String query4 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y1 ?Y2 ?Y3 ?Y\n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Professor .\n" + 
				"  ?X ub:worksFor ?Y .\n" + 
				"  ?X ub:name ?Y1 .\n" + 
				"  ?X ub:emailAddress ?Y2 .\n" + 
				"  ?X ub:telephone ?Y3}";
		
		QueryConstructor qt4 = new QueryConstructor();
		Rule r4 = qt4.translate(query4);
		PrintStream query4File = new PrintStream(new File("datalog_Query_04.txt"));
		System.setOut(query4File);
		System.out.println(r4);
		System.setOut(console);
		
		String query5 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y\n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Person .\n" + 
				"  ?X ub:memberOf ?Y}";
		
		QueryConstructor qt5 = new QueryConstructor();
		Rule r5 = qt5.translate(query5);
		PrintStream query5File = new PrintStream(new File("datalog_Query_05.txt"));
		System.setOut(query5File);
		System.out.println(r5);
		System.setOut(console);
		
		String query6 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X WHERE {?X rdf:type ub:Student}";
		
		QueryConstructor qt6 = new QueryConstructor();
		Rule r6 = qt6.translate(query6);
		PrintStream query6File = new PrintStream(new File("datalog_Query_06.txt"));
		System.setOut(query6File);
		System.out.println(r6);
		System.setOut(console);
		
		String query7 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y ?Z\n" + 
				"WHERE \n" + 
				"{?X rdf:type ub:Student .\n" + 
				"  ?Y rdf:type ub:Course .\n" + 
				"  ?X ub:takesCourse ?Y .\n" + 
				"  ?Z   \n" + 
				"  	ub:teacherOf ?Y}";
		
		QueryConstructor qt7 = new QueryConstructor();
		Rule r7 = qt7.translate(query7);
		PrintStream query7File = new PrintStream(new File("datalog_Query_07.txt"));
		System.setOut(query7File);
		System.out.println(r7);
		System.setOut(console);
		
		String query8 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y ?Z ?W \n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Student .\n" + 
				"  ?Y rdf:type ub:Department .\n" + 
				"  ?X ub:memberOf ?Y .\n" + 
				"  ?Y ub:subOrganizationOf ?W .\n" + 
				"  ?X ub:emailAddress ?Z}";
		
		QueryConstructor qt8 = new QueryConstructor();
		Rule r8 = qt8.translate(query8);
		PrintStream query8File = new PrintStream(new File("datalog_Query_08.txt"));
		System.setOut(query8File);
		System.out.println(r8);
		System.setOut(console);
		
		String query9 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y ?Z\n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Student .\n" + 
				"  ?Y rdf:type ub:Faculty .\n" + 
				"  ?Z rdf:type ub:Course .\n" + 
				"  ?X ub:advisor ?Y .\n" + 
				"  ?Y ub:teacherOf ?Z .\n" + 
				"  ?X ub:takesCourse ?Z}";
		
		QueryConstructor qt9 = new QueryConstructor();
		Rule r9 = qt9.translate(query9);
		PrintStream query9File = new PrintStream(new File("datalog_Query_09.txt"));
		System.setOut(query9File);
		System.out.println(r9);
		System.setOut(console);
		
		String query10 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y \n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Student .\n" + 
				"  ?X ub:takesCourse\n" + 
				" ?Y}";
		
		QueryConstructor qt10 = new QueryConstructor();
		Rule r10 = qt10.translate(query10);
		PrintStream query10File = new PrintStream(new File("datalog_Query_10.txt"));
		System.setOut(query10File);
		System.out.println(r10);
		System.setOut(console);
		
		String query11 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y \n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:ResearchGroup .\n" + 
				"  ?X ub:subOrganizationOf ?Y}";
		
		QueryConstructor qt11 = new QueryConstructor();
		Rule r11 = qt11.translate(query11);
		PrintStream query11File = new PrintStream(new File("datalog_Query_11.txt"));
		System.setOut(query11File);
		System.out.println(r11);
		System.setOut(console);
		
		String query12 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y ?Z \n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Chair .\n" + 
				"  ?Y rdf:type ub:Department .\n" + 
				"  ?X ub:worksFor ?Y .\n" + 
				"  ?Y ub:subOrganizationOf ?Z}";
		
		QueryConstructor qt12 = new QueryConstructor();
		Rule r12 = qt12.translate(query12);
		PrintStream query12File = new PrintStream(new File("datalog_Query_12.txt"));
		System.setOut(query12File);
		System.out.println(r12);
		System.setOut(console);
		
		String query13 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X ?Y \n" + 
				"WHERE\n" + 
				"{?X rdf:type ub:Person .\n" + 
				"  ?Y ub:hasAlumnus ?X}";
		
		QueryConstructor qt13 = new QueryConstructor();
		Rule r13 = qt13.translate(query13);
		PrintStream query13File = new PrintStream(new File("datalog_Query_13.txt"));
		System.setOut(query13File);
		System.out.println(r13);
		System.setOut(console);
		
		String query14 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>\n" + 
				"SELECT ?X\n" + 
				"WHERE {?X rdf:type ub:UndergraduateStudent}";
		
		QueryConstructor qt14 = new QueryConstructor();
		Rule r14 = qt14.translate(query14);
		PrintStream query14File = new PrintStream(new File("datalog_Query_14.txt"));
		System.setOut(query14File);
		System.out.println(r14);
		System.setOut(console);

	}

}
