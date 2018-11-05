package com.offcn.service;

import java.util.List;

import com.offcn.po.Stu;

public interface StuService {
	//读取全部学生信息
		public List<Stu> getAllStu();
		
		public void save(Stu stu);
}
