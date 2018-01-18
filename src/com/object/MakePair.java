// Name: Mong Mary Touch
// Given a list of Sentences, make pairs of keywords in each sentence
package com.object;

import java.util.ArrayList;
import java.util.List;

import com.ner.NERparser;
import com.pageCollector.PageContentCollector;

import edu.stanford.nlp.util.CoreMap;

public class MakePair {

	// make pair of related words
	public List<WordRelatable> makePair(List<Sentence> sentences) {
		List<WordRelatable> wordRelatableList = new ArrayList<WordRelatable>();
		for(Sentence sentence : sentences) {
			for(Word word1 : sentence.getWordList()) {
				for(Word word2 : sentence.getWordList()) {
					if(word1 != word2 && word2 != null) { // compare Word objects
//						System.out.println(word1.getWord() + " : " + word2.getWord());
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

	public static void main(String[] args) {

		String httpLink = "http://abcnews.go.com/Politics/analysis-year-donald-trump-redefined-presidency/story?id=52233867";
		PageContentCollector page = new PageContentCollector();
		String pageContent;
		try {
			pageContent = page.collectContent(httpLink);
			System.out.println("Content: " + pageContent);
			
			// get sentences in a document/content
			StanfordSentencesAnnotation sentenceAnnotator = new StanfordSentencesAnnotation();
			List<CoreMap> sentencesList = sentenceAnnotator.getSentencesFromDocument(pageContent);

			// generate complete sentences from document
			CompleteSentence completeSentence = new CompleteSentence();
			List<CoreMap> completeSentencesList = completeSentence.getCompleteSentence(sentencesList);

			// parse through complete sentences for NER
			NERparser nerParser = new NERparser();
			List<Sentence> nerSentencesList = nerParser.getNERfromSentence(completeSentencesList);

			MakePair mkPair = new MakePair();
			List<WordRelatable> relatedWords = mkPair.makePair(nerSentencesList);

			for (WordRelatable keyPair : relatedWords) {
				System.out.println(keyPair.getWord1().getWord() + " : " + keyPair.getWord2().getWord());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
