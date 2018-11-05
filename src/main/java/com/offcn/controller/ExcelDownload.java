package com.offcn.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.offcn.po.Stu;
import com.offcn.service.StuService;
/**
 * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7
 * @author Administrator
 *
 */
@Controller
public class ExcelDownload {

	@Autowired
	StuService service;
	
	@RequestMapping("/downloadexcel")
	public void downloadExcel(HttpServletRequest request,HttpServletResponse response){
		List<Stu> list = service.getAllStu();
		
		//获取服务器端的存储路径
		String path = request.getServletContext().getRealPath("down");
		//创建file
		File file1 = new File(path);
		//判断下载目录是否存在
		if(!file1.exists()){
			file1.mkdir();
		}
		//生命下载文件名称
		String downFile="java0625.xlsx";
		
		File file2 = new File(path+"\\"+downFile);
		
		//创建excel
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet1 = workbook.createSheet("学员成绩表");
		int rownum=0;
		//遍历数据库读取到数据集合
		for(Stu stu:list){
			XSSFRow row = sheet1.createRow(rownum);
			//设置行的内容
			row.createCell(0).setCellValue(stu.getId());
			row.createCell(1).setCellValue(stu.getName());
			row.createCell(2).setCellValue(stu.getScore());
			row.createCell(3).setCellValue(stu.getPhone());
			rownum++;
		}
		//保存工作簿
		try {
			workbook.write(new FileOutputStream(file2));
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//处理下载
		//1、通知浏览器，要下载excel
		response.setContentType("application/x-xls;charset=GBK");
		//2、让浏览器弹出一个下载框
		response.setHeader("Content-Disposition", "attachment; filename=\""+downFile+"\"");
		
		//3、设置要下载文件的长度
		response.setContentLength((int)file2.length());
		//4、读取excel写入到response
		
		//遍历输入流
		int len=-1;
		try {
			byte[] b=new byte[4096];
			BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
			BufferedInputStream in=new BufferedInputStream(new FileInputStream(file2));
			while((len=in.read(b))!=-1){
				out.write(b, 0, len);
			}
			
			out.close();
			in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
