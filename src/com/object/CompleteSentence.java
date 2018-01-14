// Name: Mong Mary Touch
// Parse through a string to get a string of complete sentences.
// A complete Sentence object is defined to have 2 NNP keywords that are also NER tagged
package com.object;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class CompleteSentence {
	public List<CoreMap> getCompleteSentence(List<CoreMap> sentences) {
		List<CoreMap> sentenceList = new ArrayList<CoreMap>();
		
		for(CoreMap sentence : sentences) {
			int count = 0;
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String pos = token.get(PartOfSpeechAnnotation.class); 
				
				if(pos.equals("NNP") || pos.equals("NNPS")) {
					count++;
				}
			}
			if (count > 1) {
				sentenceList.add(sentence);
			}
		}
		return sentenceList;
	}
}
