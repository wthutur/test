package com.gec.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.service.DeptService;
import com.gec.service.EmployeeService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.EmployeeServiceImpl;
import com.gec.service.impl.JobServiceImpl;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet(urlPatterns={"/updateEmployeeJsp.action","/employeeupdate.action","/employeelist.action","/addEmployeeJsp.action","/employeedel.action"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeService es = new EmployeeServiceImpl();
	JobService js = new JobServiceImpl();
	DeptService ds = new DeptServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1,uri.lastIndexOf("."));
		int pageNow = 1;
		if("employeelist".equals(uri)) {
			
			String jid = request.getParameter("job_id");
			Integer job_id = 0;
			if(jid != null && !jid.equals("")) {
				job_id = Integer.valueOf(jid);
			}
			
//			Integer job_id = Integer.valueOf(request.getParameter("job_id"));
			
			String name = request.getParameter("name");
			String cardId = request.getParameter("cardId");
			String sex = request.getParameter("sex");
			String phone = request.getParameter("phone");
			
			String did = request.getParameter("dept_id");
			Integer dept_id = 0;
			if(did != null && !did.equals("")) {
				dept_id = Integer.valueOf(did);
			}
			
//			Integer dept_id = Integer.valueOf(request.getParameter("dept_id"));
			
			//创建一个用户对象
			Employee emp = new Employee();
			
			Dept dept = new Dept();
			dept.setId(dept_id);
			Job job = new Job();
			job.setId(job_id);
			
			emp.setDept(dept);
			emp.setJob(job);
			emp.setName(name);
			emp.setCardId(cardId);
			emp.setSex(sex);
			emp.setPhone(phone);
			
			String index = request.getParameter("pageNow");
			pageNow = index!=null&&!index.equals("")?Integer.parseInt(index):1;
			PageBean<Employee> pb = es.findPage(pageNow, emp);
			//保存查询信息
			request.setAttribute("pb", pb);
			//保存查询对象
			request.setAttribute("employee", emp);
			
			request.setAttribute("jobList", js.findAll());
			request.setAttribute("deptList", ds.findAll());
	
			//跳转到页面
			request.getRequestDispatcher("WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}else if("updateEmployeeJsp".equals(uri)){
			int id = Integer.parseInt(request.getParameter("id"));
			Employee employee = es.findById(id);
			request.setAttribute("jobList", js.findAll());
			request.setAttribute("deptList", ds.findAll());
			request.setAttribute("employee", employee);
			request.setAttribute("txt", "用户编辑");
			request.setAttribute("val", "修改");
			
			request.getRequestDispatcher("WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
		}else if("addEmployeeJsp".equals(uri)){
			request.setAttribute("txt", "用户添加");
			request.setAttribute("val", "提交");
			request.setAttribute("jobList", js.findAll());
			request.setAttribute("deptList", ds.findAll());
			request.getRequestDispatcher("WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
		}else if("employeeupdate".equals(uri)){
			String sub = request.getParameter("sub");
			Employee emp = new Employee();
			emp.setName(request.getParameter("name"));
			emp.setCardId(request.getParameter("cardId"));
			emp.setSex(request.getParameter("sex"));
			
			emp.setJob(js.findById(Integer.parseInt(request.getParameter("job_id"))));
			
			emp.setEducation(request.getParameter("education"));
			emp.setEmail(request.getParameter("email"));
			emp.setPhone(request.getParameter("phone"));
			emp.setTel(request.getParameter("tel"));
			emp.setParty(request.getParameter("party"));
			emp.setQqNum(request.getParameter("qqNum"));
			emp.setAddress(request.getParameter("address"));
			emp.setPostCode(request.getParameter("postCode"));
			
			Date birth = null;
			try {
				birth = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			emp.setBirthday(birth);
			
			emp.setSpeciality(request.getParameter("speciality"));
			emp.setHobby(request.getParameter("hobby"));
			emp.setRemark(request.getParameter("remark"));
			
			emp.setDept(ds.findById(Integer.parseInt(request.getParameter("dept_id"))));
			
			if(sub.equals("提交")){
				es.save(emp);
			}else{
				emp.setId(Integer.parseInt(request.getParameter("id")));
				es.update(emp);
			}
			response.sendRedirect("employeelist.action");
		}else if("employeedel".equals(uri)) {
			
			Map<String, String[]> map = request.getParameterMap();
			map.forEach((k, v) -> {
				if (k.equals("empIds")) {
					for (int i = 0; i < v.length; i++) {
						es.delete(Integer.parseInt(v[i]));
					}
				}
			});
			response.sendRedirect("employeelist.action");
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
