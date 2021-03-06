package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.util.DBUtil;

public class UserDaoImpl extends DBUtil<User> implements UserDao {

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		List<User> query = query("select * from user_inf where id=?", id);
		return query.get(0);
	}

	@Override
	public PageBean<User> findPage(int pageNow, User entity) {
		PageBean<User> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql = "select count(id) from user_inf where 1=1";
		String str = "select * from user_inf where 1=1";
		if(entity.getLoginname()!=null&&!entity.getLoginname().equals("")){
			sql +=" and loginname like ?";
			str +=" and loginname like ?";
			obj.add("%"+entity.getLoginname()+"%");
		}
		if(entity.getUsername()!=null && !entity.getUsername().equals("")){
			sql += " and username like ?";
			str += " and username like ?";
			obj.add("%"+entity.getUsername()+"%");
		}
		if(entity.getStatus()>0){
			sql += " and status=?";
			str += " and status=?";
			obj.add(entity.getStatus());
		}
		pb.setRowCount(getFunction(sql, obj.toArray()));
		str += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(str, obj.toArray()));
		return pb;
	}

	@Override
	public boolean save(User entity) {
		// TODO Auto-generated method stub
		return update("insert into user_inf values(null,?,?,?,?,?)",entity.getLoginname(),entity.getPassword(),entity.getStatus(),new Date(),entity.getUsername());
	}

	@Override
	public boolean update(User entity) {
		// TODO Auto-generated method stub
		return update("update user_inf set loginname=?,password=?,username=?,status=? where id=?", entity.getLoginname(),entity.getPassword(),entity.getUsername(),entity.getStatus(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from user_inf where id=?",id);
	}
	
	@Override
	public User login(String loginname, String password) {
		List<User> list = query("select * from user_inf where loginname=? and password=?", loginname,password);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User getEntity(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setLoginname(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setStatus(rs.getInt(4));
		user.setCreatedate(rs.getDate(5));
		user.setUsername(rs.getString(6));
		return user;
	}

}
