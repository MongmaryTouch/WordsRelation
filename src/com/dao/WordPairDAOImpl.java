// Name: Mong Mary Touch
// Class that implements the DAO pattern on WordPair object 
package com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.database.DatabaseObject;
import com.object.WordPairObject;

public class WordPairDAOImpl implements WordPairDAO{

	private String table;
	private DatabaseObject dbObj = new DatabaseObject();

	public WordPairDAOImpl(String table) {
		this.table = table;
	}

	// insert to database
	private void insert(WordPairObject pair) {
		pair.setFrequency(1);
		
		String query = String.format("insert into %s (Key1, Key2, Frequency)"
				+ " values (?, ?, ?)", this.table);

		List<Object> paramPairCollection = new ArrayList<>();
		paramPairCollection.add(pair.getWord1());
		paramPairCollection.add(pair.getWord2());
		paramPairCollection.add(pair.getFrequency());
		
		dbObj.insertMap(query, paramPairCollection);
	}
	

	// delete a row from database
	@Override
	public void delete(WordPairObject pair) {
		int id = getID(pair);
		if (id == -1) System.err.println("Key pair does not exist to be deleted!");
		
		String delQuery = String.format("delete from %s where %s.id = ?", this.table, this.table);
		List<Object> paramCollection = new ArrayList<Object>();
		paramCollection.add(id);
		dbObj.executeDelete(delQuery, paramCollection);
	}


	// get ID from database
	private int getID(WordPairObject pair) {
		String idQuery = String.format("select %s.id from %s where %s.Key1 = ?" + 
				" and %s.Key2 = ?", this.table, this.table, this.table, this.table);
		
		List<Object> paramCollection = new ArrayList<Object>();
		paramCollection.add(pair.getWord1());
		paramCollection.add(pair.getWord2());
		
		return dbObj.retrieveID(idQuery, paramCollection);
	}

	
	// save key pair into database
	@Override
	public void save(WordPairObject pair) {
//		Object obj = pair.getMap();
		int id = getID(pair);
		if (id == -1) {
			insert(pair);
			return;
		}
		
		// update
		pair.setID(id);
		update(pair, pair.FREQUENCY);
	}	

	
	// update pair frequency
	@Override
	public void update(WordPairObject pair, String fieldName) {

		// get the column name 
//		Object obj = pair.get(fieldName);

		//		pair.getID();
		if(pair.getID() == null) System.err.println("Must have an ID to update!");
		
		// get Frequency by retrieving row info using id
		WordPairObject newPair = read(pair.getID());
		newPair.setFrequency(newPair.getFrequency() + 1);

		String query = String.format("update %s set %s." + fieldName +" = ? where id = ?", this.table, this.table);

		List<Object> paramPairCollection = new ArrayList<>();
		paramPairCollection.add(newPair.getFrequency());
		paramPairCollection.add(newPair.getID());

		dbObj.executeUpdate(query, paramPairCollection);
	}

	
	// Retrieve pair information from id
	@Override
	public WordPairObject read(int id) {
		Map pairMap = new HashMap<>();

		String query = String.format("select * from %s where %s.id = ?", this.table, this.table);
		List<Object> info = dbObj.executeRead(query, id);	

		List<Object> paramQuery = new ArrayList<Object>();
		paramQuery.add(id);

		WordPairObject pair = null;
		List<Map> mapList = dbObj.retrieveMap(query, paramQuery);
		if (mapList.isEmpty()) {
			System.out.println("ID does not exist!");
		} else if(mapList.size() > 1) {
			System.out.println("Multiple IDs found");
		} else {
			pair = new WordPairObject(null, null);
			pair.setMap(mapList.get(0));
		}
		return pair;
	}

	
	// retrieve the first 500 rows of the database
	@Override
	public List<WordPairObject> fetch500() {
		String query = String.format("select * from %s limit 500", this.table);
		List<Map> mapList = dbObj.retrieveMap(query, new ArrayList<Object>());
		List<WordPairObject> pairList = new ArrayList<WordPairObject>();
		
		for (Map map : mapList) {
			WordPairObject pair = new WordPairObject(null, null);
			pair.setMap(map);
			pairList.add(pair);
		}
		return pairList;
	}

}
