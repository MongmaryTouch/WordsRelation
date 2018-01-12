// Name: Mong Mary Touch
// Class that inserts and updates links in the database
package com.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.links.LinkNode;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class LinksDatabase {
	private Connection conn;
	private String table;

	// constructor that set connection and table's name
	public LinksDatabase(Connection connector, String TableName) {
		this.conn = connector;
		this.table = TableName;
	}
	
	// insert a list of links into database
	public void insertToDatabase(List<LinkNode> linksList) {
		for (LinkNode linkNode : linksList) {
			insertLink(linkNode.getGuid(), linkNode.getLink(), linkNode.getTitle(), linkNode.getDate(), "NO");
		}
	}
	
	// insert a link into the table
	public void insertLink(String guid, String link, String title, String date, String status) {
		Boolean linkExist = isLinkExist(link);
		
		if(!linkExist) {
			String query = String.format("insert into %s (guid, Link, Title, Date, Status)"
					+ " values (?, ?, ?, ?, ?)", this.table);
			try {
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
				
				preparedStmt.setString (1, guid);
				preparedStmt.setString (2, link);
				preparedStmt.setString (3, title);
				preparedStmt.setString (4, date);
				preparedStmt.setString (5, status);	// Default status
				 
				preparedStmt.execute();
				preparedStmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	// check whether the link is already exist
	public Boolean isLinkExist(String link) {
		Boolean existed = false;
		ResultSet resultSet = null;
		
		String query = String.format("SELECT EXISTS(SELECT * FROM %s "
				+ "WHERE %s.Link = '%s')", 
				this.table, this.table, link);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()) {
				existed = resultSet.getBoolean(1);
				System.out.println(existed);
			}
			preparedStmt.execute();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existed;
	}
	
	
	// get info of the link from database
	public String getLinkInfo(String link) {
		ResultSet resultSet = null;
		String linkInfo = "";

		String query = String.format("SELECT * FROM %s WHERE %s.Link = '%s'", this.table, this.table, link);

		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String guid = resultSet.getString(2);
				String http = resultSet.getString(3);
				String title = resultSet.getString(4);
				String date = resultSet.getString(5);
				String status = resultSet.getString(6);
				linkInfo = String.format("id: %d, GUID: %s, Link: %s, Title: %s, Date: %s, Status: %s", 
						id, guid, http, title, date, status);
			}
			resultSet.close();
			preparedStmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(linkInfo);
		return linkInfo;
	}
	
	
	// update status of link
	public void updateStatus(String link, String status) {
		int id = getID(link);
		
		String updateQuery = String.format("UPDATE %s SET %s.Status = ? WHERE %s.id = %d", 
				this.table, this.table, this.table, id);

		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateQuery);
			preparedStmt.setString(1, status);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// get id of the link
	public int getID(String link) {
		int id = -1;
		ResultSet resultSet = null;

		String query = String.format("SELECT %s.id FROM %s WHERE %s.Link = '%s'", 
				this.table, this.table, this.table, link);
		try {
			PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
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
	
	
	public static void main(String[] args) {
//		String guid = "guidTest-3";
		String link = "http://link-test-page3";
//		String title = "Mary is the best of the best LIKE EVER";
//		String date = "Sat, 13 Jan 2018 01:01:01 -1100";
		Connection myConn;
		try {
			myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
			LinksDatabase obj = new LinksDatabase(myConn, "LinksTable");
			obj.updateStatus(link, "YES");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
