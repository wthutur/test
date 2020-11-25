package com.gec.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.bean.Document;
import com.gec.service.DocumentService;
import com.gec.service.UserService;
import com.gec.service.impl.DocumentServiceImpl;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DocumentServlet
 */
@WebServlet(urlPatterns={"/documentaddsave.action","/updateDocument.action"})
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DocumentService ds = new DocumentServiceImpl();
		UserService us = new UserServiceImpl();
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
//		int pageNow = 1;
		if("documentaddsave".equals(uri)) {
		
		Document doc = new Document();
		boolean flag = false;  //用于判断文件是否上传成功
		/*
		 * 是通过upload上传组件进行获取内容
		 */
		//1.判断是否为二进制流提交内容
		if(ServletFileUpload.isMultipartContent(request)){
			//2.创建一个存储工厂来存储数据内容
			//DiskFileItemFactory 是一个内存数据保存工厂对象
			FileItemFactory factory = new DiskFileItemFactory();
			//3.获取到组件中servletFileUpload,将所解析的内容放入工厂,通过工厂转换为每一项文件
			ServletFileUpload upload = new ServletFileUpload(factory);
			//4.通过servletFileUpload类中的parseRequest将request中的数据转换为FileItem
			try {
				List<FileItem> list = upload.parseRequest(request);
				if(list!=null){
					//5.循环所有项,
					for (FileItem item : list) {
						//判断file是否为普通表单文件,isFormField判断 是普通表单数据则返回true 否则返回false
						if(item.isFormField()){
							if("title".equals(item.getFieldName())) {
								doc.setTitle(item.getString("utf-8"));
							}
							if("remark".equals(item.getFieldName())) {
								doc.setRemark(item.getString("utf-8"));
							}
						}else{
							
							//6.获取到要存储的文件夹
							String path = request.getServletContext().getRealPath("/upload");
							System.out.println(path);
							File file = new File(path);
							//判断该路径是否存在,如果不存在就新建
							if(!file.exists()){
								file.mkdirs();
							}
							//获取文件名
							String fileName = item.getName();
							fileName = fileName.substring(0, fileName.lastIndexOf("."))+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
							
							//将文件名保存在对象中
							doc.setFileName(fileName);
							//获得fileByte
//							ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//							InputStream is = item.getInputStream();
//							byte[] b = new byte[1024];
//							int len;
//							while ((len = is.read(b)) != -1) {
//								bos.write(b, 0, len);
//							}
//							is.close();
//							byte[] filebyte = bos.toByteArray();
							doc.setFileBytes(item.get());
							//获得fileType
							String fileType = fileName.substring(fileName.lastIndexOf("."));
							doc.setFiletype(fileType);
							//获得user
							doc.setUser(us.findById(Integer.parseInt(request.getParameter("user_id"))));
							
							File newFile = new File(file, fileName);
							//将item写出到指定文件中newFile
							item.write(newFile);
							
							flag = true;
						}
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//普通提交方式获取
//			doc.setTitle(request.getParameter("title"));
//			doc.setRemark(request.getParameter("remark"));
			
			flag = false;
		}
		if(flag){
			ds.save(doc);
			request.setAttribute("Document", doc);
			response.sendRedirect("documentlist.action");
		}else{
			response.sendRedirect("WEB-INF/jsp/document/documentadd.jsp");
		}
		}else if("updateDocument".equals(uri)) {
//			Document doc = new Document();
//			doc.setFileName(request.getParameter("filename"));
//			doc.set(request.getParameter("filename"));
//			doc.setId(Integer.parseInt(request.getParameter("id")));
//			ds.update(doc);
//			response.sendRedirect("documentlist.action");
			
			Document doc = new Document();
			boolean flag = false;  //用于判断文件是否上传成功
			/*
			 * 是通过upload上传组件进行获取内容
			 */
			//1.判断是否为二进制流提交内容
			if(ServletFileUpload.isMultipartContent(request)){
				//2.创建一个存储工厂来存储数据内容
				//DiskFileItemFactory 是一个内存数据保存工厂对象
				FileItemFactory factory = new DiskFileItemFactory();
				//3.获取到组件中servletFileUpload,将所解析的内容放入工厂,通过工厂转换为每一项文件
				ServletFileUpload upload = new ServletFileUpload(factory);
				//4.通过servletFileUpload类中的parseRequest将request中的数据转换为FileItem
				try {
					List<FileItem> list = upload.parseRequest(request);
					if(list!=null){
						//5.循环所有项,
						for (FileItem item : list) {
							//判断file是否为普通表单文件,isFormField判断 是普通表单数据则返回true 否则返回false
							if(item.isFormField()){
								if("title".equals(item.getFieldName())) {
									doc.setTitle(item.getString("utf-8"));
								}
								if("remark".equals(item.getFieldName())) {
									doc.setRemark(item.getString("utf-8"));
								}
							}else{
								//6.获取到要存储的文件夹
								String path = request.getServletContext().getRealPath("/upload");
//								System.out.println(path);
								File file = new File(path);
								//判断该路径是否存在,如果不存在就新建
								if(!file.exists()){
									file.mkdirs();
								}
								//获取文件名
								String fileName = item.getName();
								fileName = fileName.substring(0, fileName.lastIndexOf("."))+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
								
								//将文件名保存在对象中
								doc.setFileName(fileName);
								//获得fileByte
//								ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//								InputStream is = item.getInputStream();
//								byte[] b = new byte[1024];
//								int len;
//								while ((len = is.read(b)) != -1) {
//									bos.write(b, 0, len);
//								}
//								is.close();
//								byte[] filebyte = bos.toByteArray();
								doc.setFileBytes(item.get());
								//获得fileType
								String fileType = fileName.substring(fileName.lastIndexOf("."));
								doc.setFiletype(fileType);
								//获得user
								doc.setUser(us.findById(Integer.parseInt(request.getParameter("user_id"))));
								
								File newFile = new File(file, fileName);
								//将item写出到指定文件中newFile
								item.write(newFile);
								
								flag = true;
							}
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				
				flag = false;
			}
			if(flag){
				ds.update(doc);
				response.sendRedirect("documentlist.action");
			}else{
				response.sendRedirect("WEB-INF/jsp/document/documentupdate.jsp");
			}
			
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
