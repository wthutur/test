package com.gec.service;

import com.gec.bean.Document;
import com.gec.bean.PageBean;

public interface DocumentService {
	
	boolean save(Document doc);
	
	PageBean<Document> findPage(int pageNow,Document entity);
	
	Document findById(int id);
	
	boolean update(Document entity);
	
	boolean delete(int id);
	
}
