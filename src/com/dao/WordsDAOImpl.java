package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseObject;
import com.object.WordRelatable;

public class WordsDAOImpl implements WordsDAO{

	
	private DatabaseObject dbObj = new DatabaseObject();
	
	
	@Override
	public void save(example ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(example ex) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void update(WordRelatable pair, String fieldName) {
		Object obj = pair.get(fieldName);
		String query = "update WordsTable set WordsTable." + fieldName +" = ? WHERE id = ?";
		
		List<Object> paramIDcollection = new ArrayList<>();
		paramIDcollection.add(obj);
		paramIDcollection.add(pair.getID());
		
		dbObj.executeUpdate(query, paramIDcollection);
		
	}

	@Override
	public example read(long id) {
		
		// make a WordRelatable object 
		// call setMap from GenericObject
		// return that map;
		
		return null;
	}

}
