// Name: Mong Mary Touch
// Class that implements the DAO pattern on the given model
package com.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.database.DatabaseObject;
import com.object.GenericObject;

public class DAOImpl<T extends GenericObject> implements DAO<T>{

	private String table;
	private DatabaseObject dbObj = new DatabaseObject();
	private Class<T> clazz; // for reflection

	public DAOImpl(Class<T> clazz, String table) {
		this.table = table;
		this.clazz = clazz; // type
	}
	
	// insert into database
	public void insert(T object) {
		
		List<Object> paramPairCollection = new ArrayList<>();
		StringBuilder columnName = new StringBuilder();
		StringBuilder values = new StringBuilder();
		
		for(Map.Entry<String, Object> mapEntry : ((GenericObject) object).getMap().entrySet()) {
			String key = mapEntry.getKey();
			Object value = mapEntry.getValue();
			columnName.append(key).append(",");
			values.append("?,");
			paramPairCollection.add(value);
		}
		
		columnName = columnName.deleteCharAt(columnName.length() - 1); // delete the extra comma
		values = values.deleteCharAt(values.length() - 1);
		
		String query = String.format("insert into %s (" + columnName + ") values (" + values + ")", this.table);
		dbObj.insertMap(query, paramPairCollection);
	}
	
	// delete a row from database
	@Override
	public void delete(T object) {
		Object obj = ((GenericObject) object).getID();
		if (((GenericObject) object).getID() == -1) System.err.println("Key pair does not exist to be deleted!");
		
		String delQuery = String.format("delete from %s where %s.id = ?", this.table, this.table);
		List<Object> paramCollection = new ArrayList<Object>();
		paramCollection.add(obj);
		dbObj.executeDelete(delQuery, paramCollection);
	}
	
	// update object
	@Override
	public void update(T object, String fieldName) {
		Object obj = object.get(fieldName);
		
		// check if no id
		if(((GenericObject) object).getID() == null) System.err.println("Must have an ID to update!");
	
		String query = String.format("update %s set %s." + fieldName +" = ? where id = ?", this.table, this.table);
		
		List<Object> paramPairCollection = new ArrayList<>();
		paramPairCollection.add(obj);
		paramPairCollection.add(((GenericObject) object).getID());

		dbObj.executeUpdate(query, paramPairCollection);
	}
	
	// Retrieve information related to object 
	@Override
	public T read(int id) {
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
			T objToRtn = initiateInstance(); // get the constructor of the class
			objToRtn.setMap(mapList.get(0));
			return objToRtn;
		}
		return null;
	}

	// retrieve the first 500 rows of the database
	@Override
	public List<T> fetch500() {
		String query = String.format("select * from %s limit 500", this.table);
		List<Map> mapList = dbObj.retrieveMap(query, new ArrayList<Object>());
		List<T> pairList = new ArrayList<T>();
		
		for (Map map : mapList) {
			T pair = initiateInstance();
			pair.setMap(map);
			pairList.add((T) pair);
		}
		return pairList;
	}

	// return object that the template specifies
	private T initiateInstance() {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
