// Name: Mong Mary Touch
// Class that inserts, deletes and updates pair of keywords
package com.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.object.CompareWords;
import com.object.Word;
import com.object.WordPairObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/*
 * Get connection, create a statement, and execute SQL insert
 */

public class WordsDatabase {
	private Connection conn;
	private String table;


	// constructor that set connection and table's name
	public WordsDatabase(Connection connector, String TableName) {
		this.conn = connector;
		this.table = TableName;
	}


	// add a row into the database
	private void addToDB(String keyword1, String keyword2, int freq, String query) {
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString (1, keyword1);
			preparedStmt.setString (2, keyword2);
			preparedStmt.setInt (3, freq);
			preparedStmt.execute();
			preparedStmt.close();
			//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// insert data into database
	public void insert(String keyword1, String keyword2) { // query:(id, key1, key2, freq) where id is autoincrement
		int freq = 1;
		String query = String.format("INSERT INTO %s (Key1, Key2, Frequency)"
				+ " VALUES (?, ?, ?)", this.table);

		CompareWords curPair = new CompareWords();  // current pair being passed in
		WordPairObject newPair = curPair.orderPair(new Word(keyword1), new Word(keyword2)); // new pair with words ordered lexicographically
		
		String newKeyword1 = newPair.getWord1().getWord();
		String newKeyword2 = newPair.getWord2().getWord();
		
		Boolean keyPairExist = searchKeyPair(newKeyword1, newKeyword2);
		if(keyPairExist) {   // if pair already exist, update frequency
			freq = getFrequency(newKeyword1, newKeyword2) + 1; 
			updatePair(newKeyword1, newKeyword2, freq);
			return;
		}
		addToDB(newKeyword1, newKeyword2, freq, query); // else add to database for the first time
	}

	// traverse through database to search for keys pair
	private Boolean searchKeyPair(String keyword1, String keyword2) {
		Boolean pairExist = false;
		ResultSet resultSet = null;

		// compare keyword1 && keyword2
		String query = String.format("SELECT EXISTS(SELECT Key1, Key2 FROM %s "
				+ "WHERE %s.Key1 LIKE ? and %s.Key2 LIKE ? LIMIT 1)", this.table, this.table, this.table);

		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString(1, keyword1);
			preparedStmt.setString(2, keyword2);
			
			resultSet = preparedStmt.executeQuery();
			resultSet.next();
			pairExist = resultSet.getBoolean(1);

			preparedStmt.execute();
			preparedStmt.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pairExist;
	}


	// update frequency of occurrence
	public void updatePair(String keyword1, String keyword2, int num) {
		int tempID = getID(keyword1, keyword2);

		if(tempID == -1) {
			System.out.println("ERROR in getting ID.");
			//			throw new NoIDfoundException();
		}

		String updateQuery = String.format("UPDATE %s SET %s.Frequency = ? "
				+ "WHERE id = %d", this.table, this.table, tempID);

		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateQuery);
			preparedStmt.setInt(1, num);
			preparedStmt.executeUpdate();
			//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// get frequency of a pair of words
	public int getFrequency(String keyword1, String keyword2) {
		int freq = -1;
		ResultSet resultSet = null;

		// from the getID function, use it to get the frequency
		String query = String.format("SELECT %s.Frequency FROM %s "
				+ "WHERE %s.Key1 = ? and %s.Key2 = ?", 
				this.table, this.table, this.table, this.table);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString(1, keyword1);
			preparedStmt.setString(2, keyword2);
			
			resultSet = preparedStmt.executeQuery();
			resultSet.next();
			freq = resultSet.getInt(1);
			
			resultSet.close();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return freq;
	}


	// get the id of the row or words pair
	private int getID(String keyword1, String keyword2) {
		int tempID = -1;
		ResultSet resultSet = null;
		
		CompareWords curPair = new CompareWords(); 
		WordPairObject newPair = curPair.orderPair(new Word(keyword1), new Word(keyword2)); 
		
		String newKeyword1 = newPair.getWord1().getWord();
		String newKeyword2 = newPair.getWord2().getWord();

		String query = String.format("SELECT %s.id FROM %s WHERE %s.Key1 = ? and " + 
				"%s.Key2 = ?", this.table, this.table, this.table, this.table);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString(1, newKeyword1);
			preparedStmt.setString(2, newKeyword2);
			
			resultSet = preparedStmt.executeQuery();
			resultSet.next();
			tempID = resultSet.getInt(1);
			
			resultSet.close();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return tempID;
	}


	// given key1, return a list of all its related key2's
	public List<String> getRelatedWords(String keyword) {
		ResultSet resultSet = null;
		List<String> relatedList = new ArrayList<String>();

		String query = String.format("SELECT * FROM %s WHERE %s.Key1 = ?", this.table, this.table);

		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString(1, keyword);
			
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String k1 = resultSet.getString(2);
				String k2 = resultSet.getString(3);
				int freq = resultSet.getInt(4);
				relatedList.add(String.format("%d  %s  %s  %d", id, k1, k2, freq));
			}
			resultSet.close();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return relatedList;
	}


	// delete a row from database using words 
	public void deleteByWordsPair(String keyword1, String keyword2) {
		deleteByID(getID(keyword1, keyword2));
	}


	// delete a row from database using id
	public void deleteByID(int id) {
		String query = String.format("DELETE FROM %s where %s.id = ?", this.table, this.table);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.execute();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	// insert into database
	public void insertToDatabase(List<WordPairObject> keyPairsList) {
		for (WordPairObject keyPair : keyPairsList) {
			insert(keyPair.getWord1().getWord(), keyPair.getWord2().getWord());
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
		WordsDatabase obj = new WordsDatabase(myConn, "WordsTable");
		System.out.println(obj.getRelatedWords("Donald"));
	}
}
