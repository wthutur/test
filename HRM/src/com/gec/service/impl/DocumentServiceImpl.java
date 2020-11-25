package com.gec.service.impl;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.dao.DocumentDao;
import com.gec.dao.impl.DocumentDaoImpl;
import com.gec.service.DocumentService;

public class DocumentServiceImpl implements DocumentService {

	DocumentDao dd = new DocumentDaoImpl();
	
	@Override
	public boolean save(Document doc) {
		// TODO Auto-generated method stub
		return dd.save(doc);
	}

	@Override
	public PageBean<Document> findPage(int pageNow, Document entity) {
		// TODO Auto-generated method stub
		return dd.findPage(pageNow, entity);
	}

	@Override
	public Document findById(int id) {
		// TODO Auto-generated method stub
		return dd.findById(id);
	}

	@Override
	public boolean update(Document entity) {
		// TODO Auto-generated method stub
		return dd.update(entity);
	}

	@Override
	public boolean delete(int id) {
			
		return dd.delete(id);
	}

}
