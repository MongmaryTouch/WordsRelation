// Name: Mong Mary Touch
package com.test;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.database.MySqlDB;
import com.mysql.jdbc.Connection;

public class TestApp {
	public static void main(String[] args) throws SQLException {
		String myUrl = "jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false";
		Connection myConn = (Connection) DriverManager.getConnection(myUrl, "root", "admin");
		String testString = "Mary loves Thiago to the Moon and back.";

		MySqlDB sql = new MySqlDB();
		sql.insertToDatabase(myConn, "WordsTable", testString);
	}
}