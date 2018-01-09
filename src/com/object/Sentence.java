// Name: Mong Mary Touch
package com.object;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	private String sentence;
	private List<Word> wordList;
	
	public Sentence(String sentence) {
		this.sentence = sentence;
		this.wordList = new ArrayList();
	}
	
	public List<Word> getWordList() {
		return this.wordList;
	}
	
	public void addWord(Word word) {
		// split sentence and store in wordList
		wordList.add(word);
		
	}
	
}
