// Name: Mong Mary Touch
// Application controller
package com.controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database.WordsDatabase;
import com.links.LinksCollector;
import com.mysql.jdbc.Connection;
import com.ner.NERparser;
import com.object.CompleteSentence;
import com.object.MakePair;
import com.object.Sentence;
import com.object.WordRelatable;
import com.pageCollector.PageContentCollector;

public class AppControl {
	// run the program
	public void run() throws SQLException {
		List<String> linksList = new ArrayList<String>();
		try {
			String xml = "http://abcnews.go.com/abcnews/topstories";
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
			LinksCollector links = new LinksCollector();
			linksList = links.collectLinks(xml);

			if(!linksList.isEmpty()) {
				for (String link : linksList) {
					analyzeContent(link, myConn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// find all related words and insert into database
	private void analyzeContent(String link, Connection myConn) {
		try {
			// collect page's content
			PageContentCollector page = new PageContentCollector();
			String content = page.collectContent(link);
//			System.out.println("Link: " + link+ " :: "+ content);
			
			CompleteSentence sentence = new CompleteSentence();
			String completeSentences = sentence.getCompleteSentence(content);

			// get NER
			NERparser nerParser = new NERparser();
			List<Sentence> sentences = nerParser.getNERfromSentence(completeSentences);

			// generate pairs of related words
			MakePair pairs = new MakePair();
			List<WordRelatable> relatedWords = pairs.makePair(sentences);

//			for (WordRelatable keyPair : relatedWords) {
//				System.out.println(keyPair.getWord1().getWord() + " : " + keyPair.getWord2().getWord());
//			}
			
			// connect to mySql database
			WordsDatabase sql = new WordsDatabase(myConn, "WordsTable");
			sql.insertToDatabase(relatedWords);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
