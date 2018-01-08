package com.helper;

import java.sql.*;

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

	private void prepareTheDB(String keyword1, String keyword2, int freq, String query) {
		try {
			java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString (1, keyword1);
			preparedStmt.setString (2, keyword2);
			preparedStmt.setInt (3, freq);

			preparedStmt.execute();
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
		int tempID = 7;
		
		// call getID(keyword1, keyword2);

		String keyPair = String.format("%s.key1 = '%s' and "
				+ "%s.key2 = '%s' and %s.id = %d", 
				this.table, keyword1, this.table, keyword2, this.table, tempID);
//		System.out.println("keyPair: "+keyPair);
		String updateQuery = String.format("UPDATE %s "
				+ " SET Frequency = %d WHERE "
				+ keyPair, 
				this.table, num);
		try {
			java.sql.PreparedStatement preparedStmt = conn.prepareStatement(updateQuery);
			preparedStmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getFrequency() {
		int freq = -1;
		
		return freq;
	}
	
	private int getID(String keyword1, String keyword2) {
		int tempID = -1;

		return tempID;
	}

	// delete from database
	public void delete() {

	}
}
