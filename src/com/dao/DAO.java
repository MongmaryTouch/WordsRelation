// Name: Mong Mary Touch
// Implementing DAO pattern 
package com.dao;

import java.util.List;
import java.util.Map;

public interface DAO {
	
	void insert(Object pair); // create
	Map read(int id); // retrieve
	void update(Object pair, Object updatedValue, String fieldName); // update 
	void delete(Object pair); // delete
	List<Object> fetch500();
	
}
