package com.dao;

import java.util.List;

import com.object.WordPair;

public class WordPairDAO extends DAOImpl<WordPair>{

	public WordPairDAO(String table) {
		super(WordPair.class, table);
	}
	
	@Override
	public void insert(WordPair object) {
		super.insert(object);
	}
	
	@Override
	public WordPair read(int id) {
		return super.read(id);
	}
	
	@Override
	public void update(WordPair object, String fieldName) {
		super.update(object, fieldName);
	}
	
	@Override
	public void delete(WordPair pair) {
		super.delete(pair);
	}
	
	@Override
	public List<WordPair> fetch500() {
		return super.fetch500();
	}

	// update frequency
	public void updateFrequency(WordPair pair) {
		pair.setFrequency(pair.getFrequency() + 1);
	}
}
