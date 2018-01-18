package com.dao;

import com.object.WordRelatable;

public interface WordsDAO {
	
	void save(example ex);
	void delete(example ex);
	void update(WordRelatable pair, String fieldName); 
	example read(long id);
}
