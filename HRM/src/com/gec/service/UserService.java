package com.gec.service;

import com.gec.bean.PageBean;
import com.gec.bean.User;

public interface UserService{

	User findById(int id);
	
	PageBean<User> findPage(int pageNow,User entity);
	
	boolean save(User entity);
	
	boolean update(User entity);
	
	boolean delete(int id);
	
	User login(String loginname,String password);
}
