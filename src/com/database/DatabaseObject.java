// Name: Mong Mary Touch
// Database class that access the database to insert, retrieve, update and delete.
package com.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.object.Word;

public class DatabaseObject {
	private Connection conn;
	
	public DatabaseObject() {
		databaseInitializer();
	}
	
	
	// database connection setup
	private void databaseInitializer() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// execute delete from database
	public void executeDelete(String query, List<Object> objList) {
		PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			int index = 1;
			for (Object obj : objList) {
				if (obj instanceof String) {  
					preparedStmt.setString (index, (String) obj);
				} else if (obj instanceof Integer){ 
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
	}
	
	
	// execute update database
	public<T extends Object> void executeUpdate(String query, List<T> objList) {
		PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			int index = 1;
			for (Object obj : objList) {
				if (obj instanceof String) {  
					preparedStmt.setString (index, (String) obj);
				} else if (obj instanceof Integer){ 
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
	}
	
	
	// get the ID of the row 
	public<T extends Object> int retrieveID(String query, List<T> objList) {
		ResultSet resultSet = null;
		int id = -1;
		
		PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			int index = 1;
			
			for(Object obj : objList) {
				if (obj instanceof String) {  
					preparedStmt.setString (index, (String) obj);
				} else if (obj instanceof Integer){ 
					preparedStmt.setInt(index, (int) obj);
				} else if (obj instanceof Long) {
					preparedStmt.setLong(index, (long) obj);
				} else if (obj instanceof Word) {
					preparedStmt.setString(index, ((Word) obj).getWord());
				}
				index++;
			}
			
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()) {
				id = resultSet.getInt(1);
			}

			resultSet.close();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return id;
	}
	
	
	// retrieve info from database
	public List<Map> retrieveMap(String query, List<Object> paramQuery) {
		ResultSet resultSet = null;
		PreparedStatement preparedStmt;
		List<Map> mapList = new ArrayList<>();
		
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			int index = 1;
			
			for(Object obj : paramQuery) {
				if (obj instanceof String) {  
					preparedStmt.setString (index, (String) obj);
				} else if (obj instanceof Integer){ 
					preparedStmt.setInt(index, (int) obj);
				} else if (obj instanceof Long) {
					preparedStmt.setLong(index, (long) obj);
				} else if (obj instanceof Word) {
					preparedStmt.setString(index, ((Word) obj).getWord());
				} 
				index++;
			}
			resultSet = preparedStmt.executeQuery();
			
			ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
			int numColumn = resultSetMetaData.getColumnCount();
			
			List<String> rowMapper = new ArrayList<String>();
			for (int resultIndex = 1; resultIndex <= numColumn; resultIndex++) {	
				rowMapper.add(resultSetMetaData.getColumnName(resultIndex)); // getting columns name
			}
			
			while(resultSet.next()) {
				Map map = new HashMap<>();
				int columnIndex = 1;
				for(String columnName : rowMapper) {
					map.put(columnName, resultSet.getObject(columnIndex));
					columnIndex++;
				}
				mapList.add(map);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
	
	// insert into database
	public void insertMap(String query, List<Object> paramQuery) {
		ResultSet resultSet = null;
		PreparedStatement preparedStmt;
		
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			int index = 1;
			
			for(Object obj : paramQuery) {
				if (obj instanceof String) {  
					preparedStmt.setString (index, (String) obj);
				} else if (obj instanceof Integer){ 
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
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	// retrieve info from database
	public<T extends Object> List<Object> executeRead(String query, int id) { 
		ResultSet resultSet = null;
		PreparedStatement preparedStmt;
		List<Object> objInfo = new ArrayList<Object>();
		
		try {
			preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.execute();
			
			resultSet = preparedStmt.executeQuery();
			java.sql.ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int numColumn = resultSetMetaData.getColumnCount();
			
			while(resultSet.next()) {
				for (int resultIndex = 1; resultIndex <= numColumn; resultIndex++) {	
					if (resultSet.getString(resultIndex) != null) {
						objInfo.add(resultSet.getString(resultIndex));
					} else if (resultSet.getInt(resultIndex) > 0) {
						objInfo.add(resultSet.getInt(resultIndex));
					} else if (resultSet.getLong(resultIndex) > 0L) {
						objInfo.add(resultSet.getLong(resultIndex));
					}
				}
			}

			resultSet.close();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return objInfo;
	}

}
