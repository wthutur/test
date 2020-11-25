package com.gec.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns={"/userlist.action","/userupdate.action","/updateUserJsp.action","/addUserJsp.action","/logout.action","/userdel.action"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService us = new UserServiceImpl();
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1,uri.lastIndexOf("."));
		int pageNow = 1;
		if("userlist".equals(uri)){
			String loginname = request.getParameter("loginname");
			String username = request.getParameter("username");
			String sta = request.getParameter("status");
			int status = sta!=null&&!sta.equals("") ? Integer.parseInt(sta):0;
			//创建一个用户对象
			User user = new User(username,status);
			user.setLoginname(loginname);
			String index = request.getParameter("pageNow");
			pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
			PageBean<User> pb = us.findPage(pageNow, user);
			
			//保存查询信息
			request.setAttribute("pb", pb);
			//保存查询对象
			request.setAttribute("user", user);
			//跳转到页面
			request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request, response);
		}else if("updateUserJsp".equals(uri)){
			int id = Integer.parseInt(request.getParameter("id"));
			User user = us.findById(id);
				request.setAttribute("user", user);
				request.setAttribute("txt", "用户编辑");
				request.setAttribute("val", "修改");
				request.getRequestDispatcher("WEB-INF/jsp/user/useradd.jsp").forward(request, response);
		}else if("addUserJsp".equals(uri)){
			request.setAttribute("txt", "用户添加");
			request.setAttribute("val", "提交");
			request.getRequestDispatcher("WEB-INF/jsp/user/useradd.jsp").forward(request, response);
		}else if("userupdate".equals(uri)){
			String sub = request.getParameter("sub");
			User user = new User();
			user.setLoginname(request.getParameter("loginname"));
			user.setPassword(request.getParameter("password"));
			user.setUsername(request.getParameter("username"));
			user.setStatus(Integer.parseInt(request.getParameter("status")));
			if(sub.equals("提交")){
				us.save(user);
			}else{
				user.setId(Integer.parseInt(request.getParameter("id")));
				us.update(user);
			}
			response.sendRedirect("userlist.action");
		}else if("logout".equals(uri)) {
			request.getRequestDispatcher("WEB-INF/jsp/loginForm.jsp").forward(request, response);
		}else if("userdel".equals(uri)) {
			
			Map<String, String[]> map = request.getParameterMap();
			map.forEach((k, v) -> {
				if (k.equals("userIds")) {
					for (int i = 0; i < v.length; i++) {
						us.delete(Integer.parseInt(v[i]));
					}
				}
			});
			response.sendRedirect("userlist.action");
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
