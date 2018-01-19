// Name: Mong Mary Touch
// Implementing DAO pattern 
package com.dao;

import java.util.List;

import com.object.WordPairObject;

public interface WordPairDAO {
	
	void save(WordPairObject pair); // create
	WordPairObject read(int id); // retrieve
	void update(WordPairObject pair, String fieldName); // update 
	void delete(WordPairObject pair); // delete
	List<WordPairObject> fetch500();
}
