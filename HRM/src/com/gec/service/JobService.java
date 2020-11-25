package com.gec.service;

import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageBean;

public interface JobService {

	Job findById(int id);

	PageBean<Job> findPage(int pageNow, Job job);

	boolean save(Job entity);

	boolean update(Job entity);

	boolean delete(int id);

	List<Job> findAll();
}
