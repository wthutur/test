package com.gec.dao.impl;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.catalina.tribes.util.Arrays;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.EmployeeDao;
import com.gec.service.DeptService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.JobServiceImpl;
import com.gec.util.DBUtil;

public class EmployeeDaoImpl extends DBUtil<Employee> implements EmployeeDao {

	JobService js = new JobServiceImpl();
	DeptService ds = new DeptServiceImpl();
	
	@Override
	public List<Employee> findAll() {
		
		return query("select * from employee_inf");
	}

	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from employee_inf where id=?", id).get(0);
	}

	@Override
	public PageBean<Employee> findPage(int pageNow, Employee entity) {
		PageBean<Employee> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql = "select count(id) from employee_inf where 1=1";
		String str = "select * from employee_inf where 1=1";
		if(entity.getJob().getId()!=null && entity.getJob().getId()!=0 && !entity.getJob().getId().equals("")){
			sql +=" and job_id like ?";
			str +=" and job_id like ?";
			obj.add("%"+entity.getJob().getId()+"%");
		}
		if(entity.getName()!=null && entity.getName().equals("0") && !entity.getName().equals("")){
			sql +=" and name like ?";
			str +=" and name like ?";
			obj.add("%"+entity.getName()+"%");
		}
		if(entity.getCardId()!=null&& entity.getCardId().equals("0") && !entity.getCardId().equals("")){
			sql +=" and card_id like ?";
			str +=" and card_id like ?";
			obj.add("%"+entity.getCardId()+"%");
		}
		if(entity.getSex()!=null&& entity.getSex().equals("0") && !entity.getSex().equals("")){
			sql +=" and sex like ?";
			str +=" and sex like ?";
			obj.add("%"+entity.getSex()+"%");
		}
		if(entity.getPhone()!=null&& entity.getPhone().equals("0") && !entity.getPhone().equals("")){
			sql +=" and phone like ?";
			str +=" and phone like ?";
			obj.add("%"+entity.getPhone()+"%");
		}
		if(entity.getDept().getId()!=null&& entity.getDept().getId().equals("0") && !entity.getDept().getId().equals("")){
			sql +=" and name like ?";
			str +=" and name like ?";
			obj.add("%"+entity.getDept().getId()+"%");
		}
		pb.setRowCount(getFunction(sql, obj.toArray()));
		str += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(str, obj.toArray()));
		return pb;
	}

	@Override
	public boolean save(Employee entity) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return update("insert into employee_inf (DEPT_ID,JOB_ID,NAME,CARD_ID,ADDRESS,POST_CODE,TEL,PHONE,QQ_NUM,EMAIL,SEX,PARTY,BIRTHDAY,RACE,EDUCATION,SPECIALITY,HOBBY,REMARK,create_date)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				entity.getDept().getId(),entity.getJob().getId(),entity.getName(),entity.getCardId(),entity.getAddress(),entity.getPostCode(),entity.getTel(),entity.getPhone(),entity.getQqNum(),entity.getEmail(),entity.getSex(),entity.getParty(),entity.getBirthday(),entity.getRace(),entity.getEducation(),entity.getSpeciality(),entity.getHobby(),entity.getRemark(),time);
	}

	@Override
	public boolean update(Employee entity) {
		// TODO Auto-generated method stub
		return update("update employee_inf set DEPT_ID=?,JOB_ID=?,NAME=?,CARD_ID=?,ADDRESS=?,POST_CODE=?,TEL=?,PHONE=?,QQ_NUM=?,EMAIL=?,SEX=?,PARTY=?,BIRTHDAY=?,RACE=?,EDUCATION=?,SPECIALITY=?,HOBBY=?,REMARK=? where id=?", 
				entity.getDept().getId(),entity.getJob().getId(),entity.getName(),entity.getCardId(),entity.getAddress(),entity.getPostCode(),entity.getTel(),entity.getPhone(),entity.getQqNum(),entity.getEmail(),entity.getSex(),entity.getParty(),entity.getBirthday(),entity.getRace(),entity.getEducation(),entity.getSpeciality(),entity.getHobby(),entity.getRemark(),entity.getId());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from employee_inf where id=?",id);
	}

	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		Employee emp = new Employee();
//		Dept dept = new Dept();
//		Job job = new Job();
		emp.setId(rs.getInt(1));
		
		emp.setDept(ds.findById(rs.getInt(2)));
		
		emp.setJob(js.findById(rs.getInt(3)));
		
		emp.setName(rs.getString(4));
		emp.setCardId(rs.getString(5));
		emp.setAddress(rs.getString(6));
		emp.setPostCode(rs.getString(7));
		emp.setTel(rs.getString(8));
		emp.setPhone(rs.getString(9));
		emp.setQqNum(rs.getString(10));
		emp.setEmail(rs.getString(11));
		emp.setSex(rs.getString(12));
		emp.setParty(rs.getString(13));
		emp.setBirthday(rs.getDate(14));
		emp.setRace(rs.getString(15));
		emp.setEducation(rs.getString(16));
		emp.setSpeciality(rs.getString(17));
		emp.setHobby(rs.getString(18));
		emp.setRemark(rs.getString(19));
		emp.setCreateDate(rs.getDate(20));
		
		return emp;
	}

}
