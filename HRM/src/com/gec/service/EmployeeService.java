package com.gec.service;

import java.util.List;

import com.gec.bean.Employee;
import com.gec.bean.PageBean;

public interface EmployeeService {
	Employee findById(int id);

	PageBean<Employee> findPage(int pageNow, Employee emp);

	boolean save(Employee entity);

	boolean update(Employee entity);

	boolean delete(int id);
	
	List<Employee> findAll();
}
