package com.gec.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.service.DocumentService;
import com.gec.service.impl.DocumentServiceImpl;

/**
 * Servlet implementation class DocumentServlet2
 */
@WebServlet(urlPatterns = { "/documentaddJsp.action", "/documentlist.action", "/removeDocument.action",
		"/documentdownload.action", "/updateDocumentJsp.action" })
public class DocumentServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DocumentService ds = new DocumentServiceImpl();
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
		int pageNow = 1;
		if ("documentaddJsp".equals(uri)) {
			request.getRequestDispatcher("WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		} else if ("documentlist".equals(uri)) {
			String title = request.getParameter("title");

			// 创建一个用户对象
			Document doc = new Document();
			doc.setTitle(title);
			String index = request.getParameter("pageNow");
			pageNow = index != null && !index.equals("") ? Integer.parseInt(index) : 1;
			PageBean<Document> pb = ds.findPage(pageNow, doc);

			// 保存查询信息
			request.setAttribute("pb", pb);
			// 保存查询对象
			request.setAttribute("document", doc);
			// 跳转到页面
			request.getRequestDispatcher("WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		} else if ("updateDocumentJsp".equals(uri)) {
			int id = Integer.parseInt(request.getParameter("id"));
			Document doc = ds.findById(id);
			request.setAttribute("document", doc);
			request.getRequestDispatcher("WEB-INF/jsp/document/documentupdate.jsp").forward(request, response);
		} else if ("documentdownload".equals(uri)) {
			// 获取到服务器所在文件夹真实路径
			String path = request.getServletContext().getRealPath("/upload");
			// 获取要下载的文件名称
			Integer id = Integer.valueOf(request.getParameter("id"));
			String filename = ds.findById(id).getFileName();
			// 通过输入流,将文件读取
			// File.separator 这是File类提供的一个自适应斜线静态常量
			InputStream in = new FileInputStream(path + File.separator + filename);
			// 设置响应头的内容,设置响应类型为下载类型,并设置下载文件的名称
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			// 创建输出流对象,将流输出到客户端
			ServletOutputStream out = response.getOutputStream();
			// 将流输出
			int len = 0;
			byte[] bs = new byte[1024];
			while ((len = in.read(bs)) > 0) {
				out.write(bs, 0, len);
			}
			// 关闭流
			out.flush();
//			out.close();
		} else if ("removeDocument".equals(uri)) {

			String[] str = request.getParameterValues("docIds");
			
			if(str==null) {
				System.out.println("1111111");
				
			}
			
			for (String s : str) {
				ds.delete(Integer.parseInt(s));
			}
			
//			Map<String, String[]> map = request.getParameterMap();
//			map.forEach((k, v) -> {
//				if (k.equals("docIds")) {
//					for (int i = 0; i < v.length; i++) {
//						ds.delete(Integer.parseInt(v[i]));
//					}
//				}
//			});
			response.sendRedirect("documentlist.action");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
