// Name: Mong Mary Touch
// Collect text of a page
package com.pageCollector;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageContentCollector {

	// get content a page
	public String collectContent(String httpLink) throws Exception {
		String content = "";
		try {
			Document doc = Jsoup.connect(httpLink).get();
			Elements paragraphs = doc.select("p");
			for(Element paragraph : paragraphs) {
				content += paragraph.ownText() + " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static void main(String[] args) {
		
		String httpLink = "http://abcnews.go.com/Politics/analysis-year-donald-trump-redefined-presidency/story?id=52233867";
		
		PageContentCollector obj = new PageContentCollector();
		try {
			obj.collectContent(httpLink);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
