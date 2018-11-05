package com.offcn.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offcn.dao.StuDao;
import com.offcn.po.Stu;
import com.offcn.service.StuService;
@Service
public class StuServiceImpl implements StuService {

	@Autowired
	StuDao dao;
	@Override
	public List<Stu> getAllStu() {
		return dao.getAllStu();
	}
	@Override
	public void save(Stu stu) {
      dao.save(stu);		
	}

}
