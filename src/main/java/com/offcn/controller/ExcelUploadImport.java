package com.offcn.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.offcn.po.Stu;
import com.offcn.service.StuService;

@Controller
public class ExcelUploadImport {

	@Autowired
	StuService service;
	@RequestMapping("/uploadexcel")
	public String upload(@RequestParam("java0625file")MultipartFile file,HttpServletRequest request,Model model){
		//1、获取服务器端的路径
		String path = request.getServletContext().getRealPath("upload");
		
		//2、把path路径转换成File
		File file1 = new File(path);
		//3、判断上传目录是否存在
		if(!file1.exists()){
			//上传目录不存在，就创建
			file1.mkdir();
		}
		
		//4、获取上传的文件名字
		String fileName = file.getOriginalFilename();
		//5、制定上传文件存储名称和目录
		File file2 = new File(path+"\\"+fileName);
		//6、把上传文件对象的文件内容转存到制定上传目录
		try {
			file.transferTo(file2);
			System.out.println("上传成功:"+fileName);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//7、读取excel遍历数据
		try {
			Workbook workbook = WorkbookFactory.create(file2);
			Sheet sheet = workbook.getSheet("语文");
			int rownum = sheet.getPhysicalNumberOfRows();
			for(int i=0;i<rownum;i++){
				
				Row row = sheet.getRow(i);
				
				int cellnum = row.getPhysicalNumberOfCells();
				StringBuffer b=new StringBuffer();
				for(int j=0;j<cellnum;j++){
					Cell cell = row.getCell(j);
					if(cell.getCellTypeEnum()==CellType.STRING){
						b.append(cell.getStringCellValue()+"~");
					}else if(cell.getCellTypeEnum()==CellType.NUMERIC){
						DecimalFormat df = new DecimalFormat("####");
						b.append(df.format(cell.getNumericCellValue())+"~");
					}
				}
				//切开一行的字符串
				String[] ds = b.toString().split("~");
				Stu s = new Stu();
				s.setName(ds[1]);
				s.setScore(Integer.parseInt(ds[2]));
				s.setPhone(ds[3]);
				
				//调用service保存到数据库
				service.save(s);
			}
			
			workbook.close();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("filename", fileName);
		return "upload-success";
	}
}
