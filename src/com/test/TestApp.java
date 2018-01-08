package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.database.MySqlDB;
import com.helper.DatabaseHelper;
import com.ner.NERparser;
import com.nlp.NLPparser;

public class TestApp {
	public static void main(String[] args) throws SQLException {
		NLPparser nlpParser = new NLPparser();
		List<String> nlpResults = nlpParser.getPOSfromSentence("Mary is the owner of MaryTheBest Corp.", "NNP");

		// create a mysql database connection
		MySqlDB myDB = new MySqlDB();
		String myUrl = "jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false";
		Connection myConn = DriverManager.getConnection(myUrl, "root", "admin");
		
//		myDB.insertToDatabase(myUrl, myConn, nlpResults);
		
		DatabaseHelper helper = new DatabaseHelper(myUrl, myConn, "WordsTable");
//		helper.insert("Thiago", "isOkay", 3);
		helper.updateFrequency("Mary", "TheBest", 6);
	}
}