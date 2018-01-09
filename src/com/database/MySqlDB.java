// Name: Mong Mary Touch
package com.database;

import java.util.ArrayList;
import java.util.List;

import com.helper.DatabaseHelper;
import com.ner.NERparser;
import com.object.Sentence;
import com.object.Word;
import com.object.WordRelatable;
import com.mysql.jdbc.Connection;

/*
 * Get connection, create a statement, and execute SQL insert
 */

public class MySqlDB {

	// make pair of words
	public List<WordRelatable> makePair(String str) {
		NERparser nerParser = new NERparser();
		List<Sentence> sentences = nerParser.getNERfromSentence(str);
		List<WordRelatable> wordRelatableList = new ArrayList();
		
		for(Sentence sentence : sentences) {
			for(Word word1 : sentence.getWordList()) {
				for(Word word2 : sentence.getWordList()) {
					if(word1 != word2) { // compare Word objects
						WordRelatable relatable = new WordRelatable(word1, word2);
						
						Boolean isSame = searchWordRelatable(relatable, wordRelatableList);
						if(!isSame) {
							wordRelatableList.add(relatable);
						}
					}
				}
			}
		}
		return wordRelatableList;
	}
	
	private Boolean searchWordRelatable(WordRelatable wordPair, List<WordRelatable> wordRelatableList) {
		Boolean isSamePair = true;
		if (wordRelatableList.isEmpty()) return false;
		for (WordRelatable relateWords : wordRelatableList) {
			if (relateWords.getWord1() == wordPair.getWord2() && relateWords.getWord2() == wordPair.getWord1()) {
				isSamePair = true;
				return isSamePair;
			}
		}
		return false;
	}

	public void insertToDatabase(Connection myConn, String table, String str) {
		List<WordRelatable> keyPairsList = this.makePair(str);
		for (WordRelatable keyPair : keyPairsList) {
			DatabaseHelper dBase = new DatabaseHelper(myConn, table);
			dBase.insert(keyPair.getWord1().getWord(), keyPair.getWord2().getWord());
		}
	}
}
