// Name: Mong Mary Touch
// Application controller
package com.controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database.MySqlDB;
import com.linksCollector.LinksCollector;
import com.mysql.jdbc.Connection;
import com.ner.NERparser;
import com.object.MakePair;
import com.object.Sentence;
import com.object.WordRelatable;
import com.pageCollector.PageContentCollector;

public class AppControl {
	// run the program
	public void run() throws SQLException {
		List<String> linksList = new ArrayList<String>();
		try {
			String startLink = "http://abcnews.go.com/abcnews/topstories";
//			String startLink = "http://tomcat.apache.org/";
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
			findRelatedWords(startLink, myConn);
//			LinksCollector links = new LinksCollector();
//			linksList = links.collectLinks(startLink);

			linksList.add("http://abcnews.go.com/Politics/president-trump-clarifies-position-fisa-tweet-expressing-surveillance/story?id=52281564");
			if(!linksList.isEmpty()) {
				for (String link : linksList) {
					System.out.println(link);
//					findRelatedWords(link, myConn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// find all related words and insert into database
	private void findRelatedWords(String link, Connection myConn) {
		try {
			// collect page's content
			PageContentCollector page = new PageContentCollector();
			String content = page.collectContent(link);

			// get NER
			NERparser nerParser = new NERparser();
			List<Sentence> sentences = nerParser.getNERfromSentence(content);

			// generate pairs of related words
			MakePair pairs = new MakePair();
			List<WordRelatable> relatedWords = pairs.makePair(sentences);

			// connect to mySql database
			Class.forName("com.mysql.jdbc.Driver");
			
			//System.out.println("link: " + link);
			//System.out.println("cont: " + content);
//			for (WordRelatable keyPair : relatedWords) {
//				System.out.println(keyPair.getWord1().getWord() + " : " + keyPair.getWord2().getWord());
//			}
			
//			MySqlDB sql = new MySqlDB(myConn, "WordsTable");
//			sql.insertToDatabase(relatedWords);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
