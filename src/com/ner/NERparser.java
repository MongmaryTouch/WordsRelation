// Name: Mong Mary Touch
package com.ner;

import java.util.ArrayList;
import java.util.List;

import com.object.*;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

public class NERparser {
	public List<Sentence> getNERfromSentence(List<CoreMap> sentences) {
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		
		for(CoreMap sentence : sentences) {
			Sentence sentenceObj = new Sentence(sentence.toString());
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class); 
				String ner = token.get(NamedEntityTagAnnotation.class);
				
				Boolean isNer = isStrNER(ner);
				if(isNer && pos.equals("NNP")) {
					Word wordObj = new Word(word);
					wordObj.setPos(pos);
					wordObj.setNer(ner);
					sentenceObj.addWord(wordObj);
				}
			}
			sentenceList.add(sentenceObj);
		}
		return sentenceList;
	}
	
	private Boolean isStrNER(String str) {
		return (str.length() > 1) ? true:false;
	}
}
