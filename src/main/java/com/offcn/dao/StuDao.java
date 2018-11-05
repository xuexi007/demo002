package com.offcn.dao;

import java.util.List;

import com.offcn.po.Stu;

public interface StuDao {

	//读取全部学生信息
	public List<Stu> getAllStu();
	
	//插入新的学生信息
	public void save(Stu stu);
}
