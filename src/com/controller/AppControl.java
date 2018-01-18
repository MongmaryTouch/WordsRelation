// Name: Mong Mary Touch
// Application controller
package com.controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.exampleDAO;
import com.dao.exampleDAOImpl;
import com.database.LinksDatabase;
import com.database.WordsDatabase;
import com.links.LinkNode;
import com.links.LinkParser;
import com.mysql.jdbc.Connection;
import com.ner.NERparser;
import com.object.CompleteSentence;
import com.object.MakePair;
import com.object.Sentence;
import com.object.StanfordSentencesAnnotation;
import com.object.Word;
import com.object.WordRelatable;
import com.pageCollector.PageContentCollector;

import edu.stanford.nlp.util.CoreMap;

public class AppControl {
	// run the program
	public void run() throws SQLException {
		List<LinkNode> linksList = new ArrayList<LinkNode>();
		try {
			String xml = "http://abcnews.go.com/abcnews/topstories";
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/WordsRelationSql?autoReconnect=true&useSSL=false", "root", "admin");
			LinkParser linkParser = new LinkParser();
			linksList = linkParser.parseLink(xml);

			// insert links into database
			if(!linksList.isEmpty()) {
				for (LinkNode link : linksList) {
					LinksDatabase linkDB = new LinksDatabase(myConn, "LinksTable");
					linkDB.insertLink(link.getGuid(), link.getLink(), link.getTitle(), link.getDate(), "NO");
				}
			}
			
			// parse through database for status of NO
			for(LinkNode link : linksList) {
				LinksDatabase linkObj = new LinksDatabase(myConn, "LinksTable");
				Boolean linkExist = linkObj.isLinkExist(link.getLink());
				Boolean visited = linkObj.isVisited(link.getLink());
				if (linkExist && !visited) {
					analyzeContent(link.getLink(), myConn);
					linkObj.updateStatus(link.getLink(), "YES");
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
			String pageContent = page.collectContent(link);
//			System.out.println("Link: " + link+ " :: "+ pageContent);
			
			// get sentences in a document/content
			StanfordSentencesAnnotation sentenceAnnotator = new StanfordSentencesAnnotation();
			List<CoreMap> sentencesList = sentenceAnnotator.getSentencesFromDocument(pageContent);
			
			// generate complete sentences from document
			CompleteSentence completeSentence = new CompleteSentence();
			List<CoreMap> completeSentencesList = completeSentence.getCompleteSentence(sentencesList);

			// parse through complete sentences for NER
			NERparser nerParser = new NERparser();
			List<Sentence> nerSentencesList = nerParser.getNERfromSentence(completeSentencesList);

			// generate pairs of related words
			MakePair pairs = new MakePair();
			List<WordRelatable> relatedWords = pairs.makePair(nerSentencesList);

			// print out key pair
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
	
	public static void main(String[] args) throws SQLException {
		AppControl obj = new AppControl();
//		obj.run();
		exampleDAO ex = new exampleDAOImpl();
		
		WordRelatable pair = new WordRelatable(new Word("Mary"), new Word("Thiago"));
		pair.setID(5);
		pair.setFrequency(14);
		ex.update(pair, WordRelatable.WORD2);
	}
}

