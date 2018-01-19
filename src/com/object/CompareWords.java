// Name: Mong Mary Touch
// Compare 2 keywords lexicographically
package com.object;

public class CompareWords {

	public WordPairObject orderPair(Word word1, Word word2) {
		String key1 = word1.getWord();
		String key2 = word2.getWord();
		
		if(key1.compareToIgnoreCase(key2) > 0) {
			String temp = key1;
			key1 = key2;
			key2 = temp;
		}
		return new WordPairObject(new Word(key1), new Word(key2));
	}
	
	public static void main(String[] args) { 
		Word word1 = new Word("bbbb");
		Word word2 = new Word("");
		
		CompareWords wordPair = new CompareWords();
		WordPairObject result = wordPair.orderPair(word1, word2);
		System.out.println(result.getWord1().getWord() + ":" + result.getWord2().getWord());
	}
}
