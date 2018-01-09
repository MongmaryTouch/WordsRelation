// Name: Mong Mary Touch
package com.object;

public class Word {
	private String word;
	private String pos;  // part of speech
	private String ner;
	
	public Word(String str) {
		this.word = str;
	}
	
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	public String getPos() {
		return this.pos;
	}
	
	public void setNer(String ner) {
		this.ner = ner;
	}
	
	public String getNer() {
		return this.ner;
	}
	
	public String getWord() {
		return this.word;
	}
	
	
}
