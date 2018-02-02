package com.my.black.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.black.dao.AssignmentDAO;
import com.my.black.dao.CourseDAO;
import com.my.black.dao.ProfessorDAO;
import com.my.black.dao.StudentAssignmentDAO;
import com.my.black.dao.StudentDAO;
import com.my.black.dao.UserDAO;
import com.my.black.pojo.Assignment;
import com.my.black.pojo.Course;
import com.my.black.pojo.Professor;
import com.my.black.pojo.User;


@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@Autowired
	@Qualifier("courseDao")
	CourseDAO courseDao;
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	@Qualifier("professorDao")
	ProfessorDAO professorDao;
	
	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;
	
	@Autowired
	@Qualifier("assignmentDao")
	AssignmentDAO assignmentDao;
	
	@Autowired
	@Qualifier("saDao")
	StudentAssignmentDAO saDao;
	
	@RequestMapping(value="/admin/addCourse", method = RequestMethod.GET)
	public ModelAndView addCourse(HttpServletRequest request) throws Exception {
		System.out.println("admin-adcourse");
		ModelAndView mv = new ModelAndView();			
		mv.addObject("course",new Course());
		mv.setViewName("admin-addCourse");
		return mv;
	}

		
	@RequestMapping(value = "/admin/addCourse", method = RequestMethod.POST)
	protected ModelAndView addCourse(HttpServletRequest request, @ModelAttribute("course") Course course,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return new ModelAndView("admin-addCourse","course",course);
		}
		
			System.out.print("create course" + course.getcourseTitle());
		
			courseDao.createCourse(course);

			return new ModelAndView("admin-menu", "course", course);
		
	}

	
	@RequestMapping(value = "/admin/viewCourse", method = RequestMethod.GET)
	protected ModelAndView viewCourse(HttpServletRequest request, User user) throws Exception {

		List<Course> courseList=courseDao.getAllCourse();
		return new ModelAndView("admin-viewCourse","courseList",courseList);
	}
	
	
	@RequestMapping(value = "/admin/viewCourse", method = RequestMethod.POST)
	protected ModelAndView viewCourse(HttpServletRequest request, @ModelAttribute("user") Course course,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return new ModelAndView("admin-viewCourse","course",course);
		}					
			return new ModelAndView("admin-viewCourse", "course", course);
	}
	
	
	
	@RequestMapping(value = "admin/editCourse", method = RequestMethod.GET)
	protected ModelAndView editCurse(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		System.out.print(" jump to Edit Course page ");
		String id=request.getParameter("id");
		Course course=courseDao.getCourseById(Long.parseLong(id));
		mv.addObject("course",course);
		mv.setViewName("admin-editCourse");
		return mv;
	}
	
	@RequestMapping(value = "/admin/editCourse", method = RequestMethod.POST)
	protected ModelAndView editCourse(HttpServletRequest request, @ModelAttribute("course") Course course,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			String id=request.getParameter("id");
			Course c=courseDao.getCourseById(Long.parseLong(id));
			return new ModelAndView("admin-editCourse","course",course);
		}
	
			
			System.out.print("edit Course: " + course.getcourseTitle());
			Course c=courseDao.getCourseByName(course.getcourseTitle());
			c.setcourseTitle(request.getParameter("courseTitle"));
			c.setSemester(request.getParameter("semester"));
			c.setCredit(Integer.parseInt(request.getParameter("credit")));
			c.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			
	     	courseDao.update(c);
			return new ModelAndView("admin-viewCourse","courseList", courseDao.getAllCourse());	
	}

	
	
	
	@RequestMapping(value = "/admin/manageUser", method = RequestMethod.GET)
	protected ModelAndView manageUser(HttpServletRequest request, User user) throws Exception {

		List<User> userList=userDao.getAllUser();
		return new ModelAndView("admin-manageUser","userList",userList);
	}
	
	
	@RequestMapping(value = "/admin/manageUser", method = RequestMethod.POST)
	protected ModelAndView manageUser(HttpServletRequest request, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return new ModelAndView("admin-manageUser","user",user);
		}		
			
			return new ModelAndView("admin-manageUser", "user", user);

	}
	

	@RequestMapping(value = "admin/editUser", method = RequestMethod.GET)
	protected ModelAndView editUser(HttpServletRequest request) throws Exception {
		
		System.out.print(" jump to Edit User page ");
		String id=request.getParameter("id");
		User user= userDao.getUserId(Long.parseLong((id)));
		return new ModelAndView("admin-editUser","user",user);
	}
	
	@RequestMapping(value = "/admin/editUser", method = RequestMethod.POST)
	protected ModelAndView editUser(HttpServletRequest request,HttpServletResponse response, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			String id=request.getParameter("id");
			User u= userDao.getUserId(Long.parseLong((id)));
			return new ModelAndView("admin-editUser","user",u);
		}
				
			System.out.print("edit " + user.getUsername() +user.getPassword() +  user.getEmail() +user.getRole());
			
			User u=userDao.getUserId(user.getUserId());

    if(userDao.checkUserName(u.getUsername())){
			u.setUsername(request.getParameter("username"));
			u.setPassword(request.getParameter("password"));
			u.setEmail(request.getParameter("email"));
			u.setRole(request.getParameter("role"));
			userDao.update(u);
			
			return new ModelAndView("admin-manageUser","userList", userDao.getAllUser());
	}else{
  	    response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script type=\"text/javascript\"> "
                + "window.alert('Username exist, please figure out a new username.');"
                + "window.location.href='editUser.htm';"
                + "</script>");
    }
			return null;
	}

	
	
	@RequestMapping(value = "admin/deleteUser", method = RequestMethod.GET)
	protected ModelAndView deleteUser(HttpServletRequest request) throws Exception {
		
		System.out.print(" jump to delete page ");
		String id=request.getParameter("id");
		User user= userDao.getUserId(Long.parseLong((id)));
		System.out.println(" trying to delete user "+user.getUsername());
		userDao.delete(user);
		return new ModelAndView("admin-deleteUser","userList",userDao.getAllUser());
	}
	
	
	@RequestMapping(value = "admin/deleteCourse", method = RequestMethod.GET)
	protected ModelAndView deleteCourse(HttpServletRequest request) throws Exception {
		
		System.out.print(" jump to delete course page ");
		String id=request.getParameter("id");
		Course c= courseDao.getCourseById(Long.parseLong(id));
		
		System.out.println("Now there are assignment in this course: "+assignmentDao.getCourseAssignment(c).size());
		System.out.println(" trying to delete course "+c.getcourseTitle());
		courseDao.delete(c);
		return new ModelAndView("admin-deleteCourse");
	}

}
