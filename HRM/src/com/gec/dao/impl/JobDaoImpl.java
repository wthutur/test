package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.JobDao;
import com.gec.util.DBUtil;

public class JobDaoImpl extends DBUtil<Job> implements JobDao {

	@Override
	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return query("select * from job_inf");
	}

	@Override
	public Job findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from job_inf where id=?", id).get(0);
	}

	@Override
	public PageBean<Job> findPage(int pageNow, Job entity) {
		PageBean<Job> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql = "select count(id) from job_inf where 1=1";
		String str = "select * from job_inf where 1=1";
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
	public boolean save(Job entity) {
		// TODO Auto-generated method stub
		return update("insert into job_inf (name,remark)values(?,?)",entity.getName(),entity.getRemark());
	}

	@Override
	public boolean update(Job entity) {
		// TODO Auto-generated method stub
		return update("update job_inf set name=?,remark=? where id=?", entity.getName(),entity.getRemark(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from job_inf where id=?",id);
	}

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job job = new Job();
		job.setId(rs.getInt(1));
		job.setName(rs.getString(2));
		job.setRemark(rs.getString(3));
		return job;
	}

}
