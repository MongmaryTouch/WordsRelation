// Name: Mong Mary Touch
package com.test;

import java.sql.SQLException;

import com.controller.AppControl;

public class TestApp {
	public static void main(String[] args) throws SQLException {
//		String myUrl = "jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false";
//		Connection myConn = (Connection) DriverManager.getConnection(myUrl, "root", "admin");
//		String testString = "Mary loves Thiago to the Moon and back.";
//
//		MySqlDB sql = new MySqlDB();
//		sql.insertToDatabase(myConn, "WordsTable", testString);
		
//		PageCollector pageCollector = new PageCollector();
////		pageCollector.collectPage();
//		
//		
//		try {
//			pageCollector.collectPage("http://libweb.surrey.ac.uk/library/skills/Grammar%20Guide%20Leicester/page_02.htm");
////			pageCollector.getPageContent("https://www.programcreek.com/2012/05/parse-html-in-java/");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		AppControl appControl = new AppControl();
		appControl.run();
	}
}