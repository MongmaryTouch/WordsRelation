package com.ner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.coref.docreader.CoNLLDocumentReader.NamedEntityAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.patterns.surface.AnnotatedTextReader;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NERparser {
	public List<String> getNERfromSentence(String line) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		Annotation document = new Annotation(line);
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<String> keysList = new ArrayList();
		
		for(CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String ner = token.get(NamedEntityTagAnnotation.class);
				
				Boolean isNer = isStrNER(ner);
				if(isNer) {
//					keysList.add("/*" + word + " is " + ner + "*/");
					keysList.add(word);
				}
			}
		}
		return keysList;
	}
	
	private Boolean isStrNER(String str) {
		return (str.length() > 1) ? true:false;
	}
}
