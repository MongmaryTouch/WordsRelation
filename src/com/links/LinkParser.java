// Name: Mong Mary Touch
// For database
// parse an input xml to get all the links and their associated guid, title and published date.
package com.links;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkParser {
	public List<LinkNode> parseLink(String src) {
		Document doc;
		List<LinkNode> linksList = new ArrayList<LinkNode>();
		
		try {
			doc = Jsoup.connect(src).get();
			Elements items = doc.select("rss channel item");
			
			for (Element item : items) {
				LinkNode linkNode = new LinkNode();
				
				linkNode.setLink(item.getElementsByTag("link").text());
				linkNode.setGuid(item.getElementsByTag("guid").text());
				linkNode.setTitle(item.getElementsByTag("title").text());
				linkNode.setDate(item.getElementsByTag("pubDate").text());
				
				linksList.add(linkNode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linksList;
	}
}
