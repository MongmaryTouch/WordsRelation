package com.helper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

// mySQL helper functions
public class DatabaseHelper {
	private String url;
	private Connection conn;
	private String table;

	// Default constructor
	public DatabaseHelper() {
		System.out.println("Please provide connection, url, or table's name");
	}

	// constructor that set connection and url
	public DatabaseHelper(String uniformResourceLocator, Connection connector, String TableName) {
		this.url = uniformResourceLocator;
		this.conn = connector;
		this.table = TableName;
	}

	
	// add a row into the database
	private void prepareTheDB(String keyword1, String keyword2, int freq, String query) {
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setString (1, keyword1);
			preparedStmt.setString (2, keyword2);
			preparedStmt.setInt (3, freq);
			preparedStmt.execute();
			preparedStmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// insert data into database
	public void insert(String keyword1, String keyword2, int freq) { // query:(id, key1, key2, freq) where id is autoincrement
		String query = " insert into WordsTable (Key1, Key2, Frequency)"
				+ " values (?, ?, ?)";
		prepareTheDB(keyword1, keyword2, freq, query);
	}
	

	// update frequency of occurrence
	public void updateFrequency(String keyword1, String keyword2, int num) {
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
			conn.close();
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
				+ "WHERE %s.Key1 = '%s' and %s.Key2 = '%s'", 
				this.table, this.table, this.table, keyword1, this.table, keyword2);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()) {
				freq = resultSet.getInt(1);
			}
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

		String query = String.format("SELECT %s.id FROM %s WHERE %s.Key1 = '%s' and " + 
				"%s.Key2 = '%s'", this.table, this.table, this.table, keyword1, this.table, keyword2);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()) {
				tempID = resultSet.getInt(1);
			}
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
		List<String> relatedList = new ArrayList();
		
		String query = String.format("SELECT * FROM %s WHERE %s.Key1 = '%s'", this.table, this.table, keyword);
		
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
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
		String query = String.format("DELETE FROM %s where %s.id = %d", this.table, this.table, id);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.execute();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
