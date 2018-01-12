// Name: Mong Mary Touch
// Parse through a string to get a string of complete sentences.
// A complete Sentence object is defined to have 2 NNP keywords that are also NER tagged
package com.object;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class CompleteSentence {
	public String getCompleteSentence(String str) {
		String completeSentence = "";
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		Annotation document = new Annotation(str);
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence : sentences) {
			int count = 0;
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
//				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class); 
				
				if(pos.equals("NNP") || pos.equals("NNPS")) {
					count++;
				}
			}
			if (count > 1) {
				completeSentence += sentence.toString() + " ";
			}
		}
		return completeSentence;
	}
	
	public static void main(String[] args) {
		String str = "Eggworthy scrambled. Ms. Drydock has repaired. Despite Rexy’s handsome appearance, he proved to be a tough owner of Google. Because I said so.";
		CompleteSentence sentence = new CompleteSentence();
		String sentences = sentence.getCompleteSentence(str);
		System.out.println(sentences);
	}
}
