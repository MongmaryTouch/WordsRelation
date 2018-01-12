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
			Elements links = doc.select("rss channel item");
			
			for (Element link : links) {
				LinkNode linkNode = new LinkNode();
				
				linkNode.setLink(link.getElementsByTag("link").text());
				linkNode.setGuid(link.getElementsByTag("guid").text());
				linkNode.setTitle(link.getElementsByTag("title").text());
				linkNode.setDate(link.getElementsByTag("pubDate").text());
				
				linksList.add(linkNode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linksList;
	}
}
