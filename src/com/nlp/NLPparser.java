// Name: Mong Mary Touch
package com.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.object.Sentence;
import com.object.Word;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NLPparser {
	public List<Sentence> getPOSfromSentence(String line, String partOfSpeech) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation document = new Annotation(line); // create an empty Annotation just with the given text
		pipeline.annotate(document); // run all Annotators on this text

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<Sentence> sentenceList = new ArrayList();
		
		for(CoreMap sentence: sentences) {
			Sentence sentenceObj = new Sentence(sentence.toString());
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class); // this is the text of the token
				String pos = token.get(PartOfSpeechAnnotation.class); // this is the POS tag of the token
				String ner = token.get(NamedEntityTagAnnotation.class);
				
				if (line != null && pos.equals(partOfSpeech)) {
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
}
