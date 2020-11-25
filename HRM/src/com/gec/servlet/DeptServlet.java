package com.gec.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.service.DeptService;
import com.gec.service.impl.DeptServiceImpl;

/**
 * Servlet implementation class DeptServlet
 */
@WebServlet(urlPatterns={"/addDeptJsp.action","/deptUpdate.action","/deptlist.action","/deptdel.action","/updateDeptJsp.action"})
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DeptService ds = new DeptServiceImpl();
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1,uri.lastIndexOf("."));
		int pageNow = 1;
		if("addDeptJsp".equals(uri)) {
			request.setAttribute("txt", "部门添加");
			request.setAttribute("val", "提交");
			request.getRequestDispatcher("WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		}else if("updateDeptJsp".equals(uri)){
			int id = Integer.parseInt(request.getParameter("id"));
			Dept dept = ds.findById(id);
			request.setAttribute("dept", dept);
			request.setAttribute("txt", "用户编辑");
			request.setAttribute("val", "修改");
			request.getRequestDispatcher("WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		}else if("deptlist".equals(uri)){
			String name = request.getParameter("name");
			
			//创建一个用户对象
			Dept dept = new Dept(name);
			String index = request.getParameter("pageNow");
			pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
			PageBean<Dept> pb = ds.findPage(pageNow, dept);
			
			//保存查询信息
			request.setAttribute("pb", pb);
			//保存查询对象
			request.setAttribute("dept", dept);
			//跳转到页面
			request.getRequestDispatcher("WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		}else if("deptUpdate".equals(uri)) {
			String sub = request.getParameter("sub");
			Dept dept = new Dept();
			dept.setName(request.getParameter("name"));
			dept.setRemark(request.getParameter("remark"));
			if(("提交").equals(sub)){
				ds.save(dept);
			}else{
				dept.setId(Integer.parseInt(request.getParameter("id")));
				ds.update(dept);
			}
			response.sendRedirect("deptlist.action");
		}else if("deptdel".equals(uri)) {
			
			Map<String, String[]> map = request.getParameterMap();
			map.forEach((k, v) -> {
				if (k.equals("deptIds")) {
					for (int i = 0; i < v.length; i++) {
						ds.delete(Integer.parseInt(v[i]));
					}
				}
			});
			response.sendRedirect("deptlist.action");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
