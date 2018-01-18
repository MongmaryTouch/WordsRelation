package com.object;

import java.util.HashMap;
import java.util.Map;

public class GenericObject {
	private Map<String, Object> map = new HashMap<String, Object>();
	public static String ID = "id"; // in database this is a string
	
	public void put(String key, Object value) {
		map.put(key, value);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public void setID(int id) {
		this.put(ID, id);
	}
	
	public int getID() {
		return (int) this.get(ID);
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	// add more code
}
