// Name: Mong Mary Touch
package com.object;

public class WordRelatable {
	private Word word1;
	private Word word2;
	
	public WordRelatable(Word keyword1, Word keyword2) {
		this.word1 = keyword1;
		this.word2 = keyword2;
	}
	
	public void setWord1(Word word1) {
		this.word1 = word1;
	}
	
	public Word getWord1() {
		return this.word1;
	}
	
	public void setWord2(Word word2) {
		this.word2 = word2;
	}
	
	public Word getWord2() {
		return this.word2;
	}
	
}
