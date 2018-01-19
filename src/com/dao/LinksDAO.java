package com.dao;

import com.links.LinkNode;

public interface LinksDAO {
	void save(LinkNode link); // create
	LinkNode read(int id); // retrieve
	void update(LinkNode link, String fieldName); // update
	void delete(LinkNode link); // delete
}
