package com.test;

//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.helper.DatabaseHelper;

import com.mysql.jdbc.Connection;

public class TestApp {
	public static void main(String[] args) throws SQLException {
//		NLPparser nlpParser = new NLPparser();
//		List<String> nlpResults = nlpParser.getPOSfromSentence("Mary is the owner of MaryTheBest Corp.", "NNP");
//		System.out.println(nlpResults);
		
		// create a mysql database connection
		String myUrl = "jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false";
		Connection myConn = (Connection) DriverManager.getConnection(myUrl, "root", "admin");
		
//		myDB.insertToDatabase(myUrl, myConn, nlpResults);
		
		DatabaseHelper helper = new DatabaseHelper(myUrl, myConn, "WordsTable");
		helper.insert("Thiago", "isOkay", 5);
//		helper.updateFrequency("Mary", "TheBest", 1);
//		helper.getRelatedWords("Mary");
//		helper.getFrequency("Mary", "TheBest3");
//		helper.deleteByWordsPair("Mary", "TheBest2");
	}
}