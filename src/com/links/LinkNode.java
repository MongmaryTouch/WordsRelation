// Name: Mong Mary Touch
// RSS information of a link
package com.links;

import com.object.GenericObject;

public class LinkNode extends GenericObject{
	private String guid;
	private String link;
	private String title;
	private String date;
	
	// set GUID of the link
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	// get GUID of the link
	public String getGuid() {
		return this.guid;
	}
	
	// set link
	public void setLink(String link) {
		this.link = link;
	}
	
	// get link
	public String getLink() {
		return this.link;
	}
	
	// set title
	public void setTitle(String title) {
		this.title = title;
	}
	
	// get title of the page
	public String getTitle() {
		return this.title;
	}
	
	// set date
	public void setDate(String date) {
		this.date = date;
	}
	
	// get date 
	public String getDate() {
		return this.date;
	}
}
