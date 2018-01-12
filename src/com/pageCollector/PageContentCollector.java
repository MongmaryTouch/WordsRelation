// Name: Mong Mary Touch
// Collect text of a page
package com.pageCollector;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageContentCollector {

	// get content a page
	public String collectContent(String httpLink) throws Exception {
		try {
			Document doc = Jsoup.connect(httpLink).get();
			return doc.text();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
