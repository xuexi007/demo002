package com.offcn.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.dao.StuDao;
import com.offcn.po.Stu;
//******************************************************88
public class TestDao {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");

		StuDao dao = context.getBean(StuDao.class);
		Stu s = new Stu();
		s.setName("测试新增");
		s.setPhone("8888888888");
		s.setScore(99);
		dao.save(s);
		
		List<Stu> list = dao.getAllStu();
		for (Stu stu : list) {
			System.out.println("姓名:"+stu.getName()+" 成绩:"+stu.getScore());
		}
	}

}
