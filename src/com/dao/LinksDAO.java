package com.dao;

import java.util.List;

import com.links.LinkNode;

public class LinksDAO extends DAOImpl<LinkNode>{

	public LinksDAO(Class<LinkNode> clazz, String table) {
		super(LinkNode.class, table);
	}
	
	@Override
	public void insert(LinkNode object) {
		// TODO Auto-generated method stub
		super.insert(object);
	}
	
	@Override
	public LinkNode read(int id) {
		// TODO Auto-generated method stub
		return super.read(id);
	}
	
	@Override
	public void update(LinkNode object, String fieldName) {
		// TODO Auto-generated method stub
		super.update(object, fieldName);
	}
	
	@Override
	public void delete(LinkNode object) {
		// TODO Auto-generated method stub
		super.delete(object);
	}
	
	@Override
	public List<LinkNode> fetch500() {
		// TODO Auto-generated method stub
		return super.fetch500();
	}
}
