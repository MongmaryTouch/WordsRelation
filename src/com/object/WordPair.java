// Name: Mong Mary Touch
package com.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordPair extends GenericObject{

	public static final String WORD1 = "Key1";
	public static final String WORD2 = "Key2";
	public static final String FREQUENCY = "Frequency";
	public static final String ID = "id";

	
	public WordPair(Word keyword1, Word keyword2) {
		this.setWord1(keyword1);
		this.setWord2(keyword2);
	}
	
	public void setWord1(Word word1) {
		this.put(WORD1, word1);
	}
	
	public Word getWord1() {
		return (Word) this.get(WORD1);
	}
	
	public void setWord2(Word word2) {
		this.put(WORD2, word2);
	}
	
	public Word getWord2() {
		return (Word) this.get(WORD2);
	}
	
	public void setFrequency(int freq) {
		this.put(FREQUENCY, freq);
	}
	
	public int getFrequency() {
		return (int) this.get(FREQUENCY);
	}

	@Override
	public void setMap(Map map) {
		super.setMap(map);
		setWord1(new Word((String) map.get(WORD1)));
		setWord2(new Word((String) map.get(WORD2)));
	}
	
//	@Override
//	public List<Object> getMap() {
//		super.getMap();
//		
//		List<Object> mapList = new ArrayList();
//		
//		return mapList;
//	}
	
}
