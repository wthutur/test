package com.gec.dao.impl;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.dao.DocumentDao;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;
import com.gec.util.DBUtil;

public class DocumentDaoImpl extends DBUtil<Document> implements DocumentDao {

	UserService us = new UserServiceImpl();
	
	@Override
	public boolean save(Document doc) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
//		System.out.println(doc.getTitle());
//		System.out.println(doc.getFileName());
//		System.out.println(doc.getFiletype());
//		System.out.println(doc.getFileBytes());
//		System.out.println(doc.getRemark());
//		System.out.println(time);
//		System.out.println(doc.getUser().getId());
		return update("insert into document_inf (title,filename,filetype,filebytes,remark,create_date,user_id)values(?,?,?,?,?,?,?)", doc.getTitle(),doc.getFileName(),doc.getFiletype(),doc.getFileBytes(),doc.getRemark(),time,doc.getUser().getId());
	}

	@Override
	public List<Document> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from document_inf where id=?", id).get(0);
	}

	@Override
	public PageBean<Document> findPage(int pageNow, Document entity) {
		PageBean<Document> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql = "select count(id) from document_inf where 1=1";
		String str = "select * from document_inf where 1=1";
		if(entity.getTitle()!=null&&!entity.getTitle().equals("")){
			sql +=" and loginname like ?";
			str +=" and loginname like ?";
			obj.add("%"+entity.getTitle()+"%");
		}
		pb.setRowCount(getFunction(sql, obj.toArray()));
		str += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(str, obj.toArray()));
		return pb;
	}

	@Override
	public boolean update(Document doc) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return update("update document_inf set title=?,filename=?,filetype=?,filebytes=?,remark=?,create_date=?,user_id=? where id=?", doc.getTitle(),doc.getFileName(),doc.getFiletype(),doc.getFileBytes(),doc.getRemark(),time,doc.getUser().getId(),doc.getId());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from document_inf where id=?",id);
	}

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		Document doc = new Document();
		doc.setId(rs.getInt(1));
		doc.setTitle(rs.getString(2));
		doc.setFileName(rs.getString(3));
		doc.setFiletype(rs.getString(4));
		doc.setFileBytes(rs.getBytes(5));
		doc.setRemark(rs.getString(6));
		doc.setCreateDate(rs.getDate(7));
		doc.setUser(us.findById(rs.getInt(8)));
		
		return doc;
	}

	

}
