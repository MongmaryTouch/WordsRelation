// Name: Mong Mary Touch
// Get all the links associated with a given source site
package com.links;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinksCollector {

	public List<String> collectLinks(String src) {
		List<String> linksList = new ArrayList<>();
		Document doc;
		try {
			doc = Jsoup.connect(src).get();
			Elements links = doc.select("rss channel item link");
			
			for (Element link : links) {
				if (link.tagName().equals("link")) {
					linksList.add(link.text());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linksList;
	}
}
