package com.gec.service;

import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;

public interface DeptService {

	Dept findById(int id);

	PageBean<Dept> findPage(int pageNow, Dept dept);

	boolean save(Dept entity);

	boolean update(Dept entity);

	boolean delete(int id);

	List<Dept> findAll();
}
