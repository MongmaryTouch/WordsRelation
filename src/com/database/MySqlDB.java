package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/*
 * Get connection, create a statement, and execute SQL insert
 */

public class MySqlDB {

	// update frequency of the related keywords found
	public int updateFreq(int freq) {
		return freq++;
	}

	private static List<? extends List<String>> makePair(List<String> strList) {
		final List<? extends List<String>> pairList = new ArrayList<>();

		for (int fromIndex = 0; fromIndex < strList.size()-1; fromIndex++) {
			for (int j = fromIndex+1; j < strList.size(); j++) {
				System.out.println(fromIndex + strList.get(fromIndex) + " , "+ j+strList.get(j));
				//pairList.add(strList.get(fromIndex), strList.get(j));
				//pairList.add(strList.subList(fromIndex, j));
				//System.out.println("After: " + pairList);
			}
			
//			pairList.add(strList.subList(fromIndex, j));
			
		}
		
		return pairList;
	}
	
	

	public static void insertToDatabase(String myUrl, Connection myConn, List<String> wordsList) throws SQLException {

		// makePair()
//		List<List<String>> keyPairs = makePair(wordsList);

//		for (List<String> keyPair : keyPairs) {
			
			// check each pair to see if one of the keyword exist in the database
//			System.out.println("Pair: " + keyPair);
			
			
//			// check to see if the keywords are already in the table
//			String query = " insert into WordsTable (Key1, Key2, Frequency)"
//					+ " values (?, ?, ?)";
//
//			// create the mysql insert preparedstatement
//			PreparedStatement preparedStmt;
//			try {
//				preparedStmt = myConn.prepareStatement(query);
//
//				// call a function to determine their relationship in the database. use call procedure
//				//preparedStmt.setInt(1, 1);
//				preparedStmt.setString (1, "key1");
//				preparedStmt.setString (2, "key2");
//				preparedStmt.setInt (3, 1);
//
//				// callable statement
//				// Callable myStmt2 = myConn.prepareCall("{call updateFunction(?,?)}");
//
//				// execute the preparedstatement
//				preparedStmt.execute();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		myConn.close();
	}
}
