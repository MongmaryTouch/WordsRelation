// Name: Mong Mary Touch
// Implementing DAO pattern 
package com.dao;

import java.util.List;

public interface DAO<T>{
	
	void insert(T pair); // create
	T read(int id); // retrieve
	void delete(T pair); // delete
	List<T> fetch500();
	void update(T object, String fieldName);
	
}
