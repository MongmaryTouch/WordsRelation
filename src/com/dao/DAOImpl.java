// Name: Mong Mary Touch
// Class that implements the DAO pattern on the given model
package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.database.DatabaseObject;
import com.object.GenericObject;

public class DAOImpl implements DAO{

	private String table;
	private DatabaseObject dbObj = new DatabaseObject();

	public DAOImpl(String table) {
		this.table = table;
	}
	
	// insert into database
	public void insert(Object pair) {
		
		List<Object> paramPairCollection = new ArrayList<>();
		StringBuilder columnName = new StringBuilder();
		StringBuilder values = new StringBuilder();
		
		for(Map.Entry<String, Object> mapEntry : ((GenericObject) pair).getMap().entrySet()) {
			String key = mapEntry.getKey();
			Object value = mapEntry.getValue();
			columnName.append(key).append(",");
			values.append("?,");
			paramPairCollection.add(value);
		}
		
		columnName = columnName.deleteCharAt(columnName.length()-1); // delete the extra comma
		values = values.deleteCharAt(values.length()-1);
		
		String query = String.format("insert into %s (" + columnName + ") values (" + values + ")", this.table);
		dbObj.insertMap(query, paramPairCollection);
	}
	
	// delete a row from database
	@Override
	public void delete(Object pair) {
		Object obj = ((GenericObject) pair).getID();
		if (((GenericObject) pair).getID() == -1) System.err.println("Key pair does not exist to be deleted!");
		
		String delQuery = String.format("delete from %s where %s.id = ?", this.table, this.table);
		List<Object> paramCollection = new ArrayList<Object>();
		paramCollection.add(obj);
		dbObj.executeDelete(delQuery, paramCollection);
	}
	
	// update object
	@Override
	public void update(Object pair, Object updatedValue, String fieldName) {

		// check if no id
		if(((GenericObject) pair).getID() == null) System.err.println("Must have an ID to update!");
	
		String query = String.format("update %s set %s." + fieldName +" = ? where id = ?", this.table, this.table);
		
		List<Object> paramPairCollection = new ArrayList<>();
		paramPairCollection.add(updatedValue);
		paramPairCollection.add(((GenericObject) pair).getID());

		dbObj.executeUpdate(query, paramPairCollection);
	}
	
	// Retrieve information related to object 
	@Override
	public Map read(int id) {
		String query = String.format("select * from %s where %s.id = ?", this.table, this.table);
		List<Object> info = dbObj.executeRead(query, id);	

		List<Object> paramQuery = new ArrayList<Object>();
		paramQuery.add(id);

		List<Map> mapList = dbObj.retrieveMap(query, paramQuery);
		if (mapList.isEmpty()) {
			System.err.println("ID does not exist!");
		} else if(mapList.size() > 1) {
			System.err.println("Multiple IDs found");
		} else {
			return mapList.get(0);
		}
		return null;
	}

	// retrieve the first 500 rows of the database
	@Override
	public List<Object> fetch500() {
		String query = String.format("select * from %s limit 500", this.table);
		List<Map> mapList = dbObj.retrieveMap(query, new ArrayList<Object>());
		List<Object> pairList = new ArrayList<Object>();
		
		for (Map map : mapList) {
//			Object pair = new Object();
//			((GenericObject) pair).setMap(map);
//			pairList.add(pair);
			pairList.add(map);
		}
		return pairList;
	}
}
