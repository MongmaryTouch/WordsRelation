package com.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.object.Word;

public class DatabaseObject {
	private Connection conn;
	
	public DatabaseObject() {
		databaseInitializer();
	}
	
	private void databaseInitializer() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public<T extends Object> Boolean executeUpdate(String query, List<T> objList) {
		
		PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			int index = 1;
			for (Object obj : objList) {
				if (obj instanceof String) {  // MAGIC
					preparedStmt.setString (index, (String) obj);
				} else if (obj instanceof Integer){ // ANOTHER MAGIC
					preparedStmt.setInt(index, (int) obj);
				} else if (obj instanceof Long) {
					preparedStmt.setLong(index, (long) obj);
				} else if (obj instanceof Word) {
					preparedStmt.setString(index, ((Word) obj).getWord());
				}
				index++;
			}

			preparedStmt.execute();
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
		
		
	
	

	// constructor that set connection and table's name
//	public DatabaseObject(Connection connector, String TableName) {
//		this.conn = connector;
//		this.table = TableName;
//	}

}
