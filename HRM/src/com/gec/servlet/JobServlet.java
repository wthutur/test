package com.gec.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.service.JobService;
import com.gec.service.impl.JobServiceImpl;

/**
 * Servlet implementation class JobServlet
 */
@WebServlet(urlPatterns={"/updateJobJsp.action","/jobupdate.action","/joblist.action","/addJobJsp.action","/jobdel.action"})
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JobService js = new JobServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1,uri.lastIndexOf("."));
		int pageNow = 1;
		if("joblist".equals(uri)) {
			String name = request.getParameter("name");
			//创建一个用户对象
			Job job = new Job(name);
			String index = request.getParameter("pageNow");
			pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
			PageBean<Job> pb = js.findPage(pageNow, job);
			
			//保存查询信息
			request.setAttribute("pb", pb);
			//保存查询对象
			request.setAttribute("job", job);
			//跳转到页面
			request.getRequestDispatcher("WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if("updateJobJsp".equals(uri)){
			int id = Integer.parseInt(request.getParameter("id"));
			Job job = js.findById(id);
			request.setAttribute("job", job);
			request.setAttribute("txt", "职位编辑");
			request.setAttribute("val", "修改");
			request.getRequestDispatcher("WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
		}else if("addJobJsp".equals(uri)){
			request.setAttribute("txt", "职位添加");
			request.setAttribute("val", "提交");
			request.getRequestDispatcher("WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
		}else if("jobupdate".equals(uri)){
			String sub = request.getParameter("sub");
			Job job = new Job();
			job.setName(request.getParameter("name"));
			job.setRemark(request.getParameter("remark"));
			if(sub.equals("提交")){
				js.save(job);
			}else{
				job.setId(Integer.parseInt(request.getParameter("id")));
				js.update(job);
			}
			response.sendRedirect("joblist.action");
		}else if("jobdel".equals(uri)) {
			Map<String, String[]> map = request.getParameterMap();
			map.forEach((k, v) -> {
				if (k.equals("jobIds")) {
					for (int i = 0; i < v.length; i++) {
						js.delete(Integer.parseInt(v[i]));
					}
				}
			});
			response.sendRedirect("joblist.action");
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
