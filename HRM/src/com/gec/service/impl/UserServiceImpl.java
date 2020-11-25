package com.gec.service.impl;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.dao.impl.UserDaoImpl;
import com.gec.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao ud = new UserDaoImpl();
	
	@Override
	public User findById(int id) {
		return ud.findById(id);
	}

	@Override
	public PageBean<User> findPage(int pageNow, User entity) {
		return ud.findPage(pageNow, entity);
	}

	@Override
	public boolean save(User entity) {
		return ud.save(entity);
	}

	@Override
	public boolean update(User entity) {
		return ud.update(entity);
	}

	@Override
	public boolean delete(int id) {
			
		return ud.delete(id);
	}

	@Override
	public User login(String loginname, String password) {
		return ud.login(loginname, password);
	}

}
