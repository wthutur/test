package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.dao.DeptDao;
import com.gec.util.DBUtil;

public class DeptDaoImpl extends DBUtil<Dept> implements DeptDao {

	@Override
	public List<Dept> findAll() {
		// TODO Auto-generated method stub
		return query("select * from dept_inf");
	}

	@Override
	public Dept findById(int id) {
		
		return query("select * from dept_inf where id=?", id).get(0);
	}

	@Override
	public PageBean<Dept> findPage(int pageNow, Dept entity) {
		PageBean<Dept> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql = "select count(id) from dept_inf where 1=1";
		String str = "select * from dept_inf where 1=1";
		if(entity.getName()!=null&&!entity.getName().equals("")){
			sql +=" and name like ?";
			str +=" and name like ?";
			obj.add("%"+entity.getName()+"%");
		}
		pb.setRowCount(getFunction(sql, obj.toArray()));
		str += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(str, obj.toArray()));
		return pb;
	}

	@Override
	public boolean save(Dept entity) {
		// TODO Auto-generated method stub
		return update("insert into dept_inf (name,remark)values(?,?)",entity.getName(),entity.getRemark());
	}

	@Override
	public boolean update(Dept entity) {
		// TODO Auto-generated method stub
		return update("update dept_inf set name=?,remark=? where id=?", entity.getName(),entity.getRemark(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from dept_inf where id=?",id);
	}

	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept dept = new Dept();
		dept.setId(rs.getInt(1));
		dept.setName(rs.getString(2));
		dept.setRemark(rs.getString(3));
		return dept;
	}

}
