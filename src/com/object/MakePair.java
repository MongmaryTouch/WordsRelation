// Name: Mong Mary Touch
// Given a list of Sentences, make pairs of keywords in each sentence
package com.object;

import java.util.ArrayList;
import java.util.List;

public class MakePair {
	// make pair of related words
	public List<WordRelatable> makePair(List<Sentence> sentences) {
		List<WordRelatable> wordRelatableList = new ArrayList<WordRelatable>();

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

	// search the pair list if the pair is repeated
	private Boolean searchWordRelatable(WordRelatable wordPair, List<WordRelatable> wordRelatableList) {
		if (wordRelatableList.isEmpty()) return false; // no repeated
		for (WordRelatable relateWords : wordRelatableList) {
			if (relateWords.getWord1() == wordPair.getWord2() && relateWords.getWord2() == wordPair.getWord1()) {
				return true;
			}
		}
		return false;
	}
}
