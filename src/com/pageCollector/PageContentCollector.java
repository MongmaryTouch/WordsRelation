// Name: Mong Mary Touch
// Collect text of the page
package com.pageCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.print.attribute.standard.JobOriginatingUserName;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageContentCollector {
	private HttpClient client = HttpClientBuilder.create().build();
	private final String USER_AGENT = "Mozilla/5.0";

	public String collectContent(String httpLink) throws Exception {
		try {
			Document doc = Jsoup.connect(httpLink).get();
			Elements elements = doc.select("*");
			
			for (Element element : elements) {
				if(!element.text().startsWith("http") ) {
//						&& !element.text().equals("") 
//						&& (element.text().length() > 2)) {
						System.out.println("tag: " + element.tagName() + " " + element.text());
				}
			}
			
			
			// check news link title and date to see if it has already been visited
			// guid
			
			
			// need to parse through the content to see if every sentence is a complete sentence
			// rule one: 1 sub and 1 verb
			// modified rule two: if it has 2 NNP keywords
			
			return doc.text();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public String getPageContent(String url) throws Exception {

		HttpGet request = new HttpGet(url);

		request.setHeader("User-Agent", USER_AGENT);
		request.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "en-US,en;q=0.5");

		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
}
