package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.dao.DeptDao;
import com.gec.dao.impl.DeptDaoImpl;
import com.gec.service.DeptService;

public class DeptServiceImpl implements DeptService{

	DeptDao dd = new DeptDaoImpl();
	
	
	@Override
	public Dept findById(int id) {
		return dd.findById(id);
	}


	@Override
	public PageBean<Dept> findPage(int pageNow, Dept dept) {
		
		return dd.findPage(pageNow, dept);
	}


	@Override
	public boolean save(Dept entity) {
		return dd.save(entity);
	}


	@Override
	public boolean update(Dept entity) {
		return dd.update(entity);
	}


	@Override
	public boolean delete(int id) {
		return dd.delete(id);
	}


	@Override
	public List<Dept> findAll() {
		// TODO Auto-generated method stub
		return dd.findAll();
	}

}
