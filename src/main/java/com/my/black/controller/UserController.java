package com.my.black.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.black.dao.ProfessorDAO;
import com.my.black.dao.StudentDAO;
import com.my.black.dao.UserDAO;
import com.my.black.pojo.User;
import com.my.black.pojo.Professor;
import com.my.black.pojo.Student;

@Controller
// @RequestMapping("/user/*")
public class UserController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("professorDao")
	ProfessorDAO professorDao;
	
	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;
	
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser(User user) throws Exception {
		
		System.out.print("registerUser");
		return new ModelAndView("register-user", "user", user);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user
			) throws Exception {

		
		try {
			
			System.out.print("registerNewUser" + user.getUsername() +user.getPassword() +  user.getEmail() +user.getRole());
      if(userDao.checkUserName(user.getUsername())){
			String role = request.getParameter("role");
			
			if (role.equals("professor")) {
	
				Professor pr = new Professor();
				pr.setUsername(user.getUsername());
				pr.setPassword(user.getPassword());
				pr.setEmail(user.getEmail());
				pr.setRole(user.getRole());
				professorDao.create(pr);

				request.getSession().setAttribute("user", pr);
				return new ModelAndView("register-success", "user", pr);
				
			} else if (role.equals("student")) {
				
				Student s = new Student();
				s.setUsername(user.getUsername());
				s.setPassword(user.getPassword());
				s.setEmail(user.getEmail());
				s.setRole(user.getRole());
				studentDao.create(s);

				request.getSession().setAttribute("user", s);
				return new ModelAndView("register-success", "user", s);

			} else {
				User u = userDao.registerUser(user);
				request.getSession().setAttribute("user", u);
				return new ModelAndView("register-success", "user", u);
			}

      }else{
    	  response.setContentType("text/html;charset=utf-8");
          PrintWriter out = response.getWriter();
          out.print("<script type=\"text/javascript\"> "
                  + "window.alert('Username exist, please figure out a new username.');"
                  + "window.location.href='register.htm';"
                  + "</script>");
      }

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("register-error", "errorMessage", "error while login");
		}
		return null;
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	protected ModelAndView loginUser() throws Exception {
		
		System.out.print("loginUser");
		return new ModelAndView("index");
	}
	
	

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	protected ModelAndView loginUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
			System.out.print("loginUser");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User u = userDao.getUser(username, password);
			if (u == null) {
				System.out.println(" UserName/Password does not exist ");
				session.setAttribute("errorMessage", "UserName/Password does not exist");				
				  response.setContentType("text/html;charset=utf-8");
		          PrintWriter out = response.getWriter();
		          out.print("<script type=\"text/javascript\"> "
		                  + "window.alert('Username/password does not exist.');"
		                  + "window.location.href='loginAgain.htm';"
		                  + "</script>");
		         			}
			session.setAttribute("user", u);			
			
			if (u.getRole().equals("admin")) {
				ModelAndView mv = new ModelAndView();				
				mv.setViewName("admin-menu");
				return mv;
			}

			else if (u.getRole().equals("professor")) {
				return new ModelAndView("professor-menu");
			}

			else{
				return new ModelAndView("student-menu");
			}		
	}
	
	@RequestMapping(value = "/user/loginAgain", method = RequestMethod.GET)
	protected ModelAndView loginUserAgain() throws Exception {
		
		System.out.print("jump to loginUserAgain");
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/user/loginAgain", method = RequestMethod.POST)
	protected ModelAndView loginUserAgain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
			System.out.print("loginUser Again");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User u = userDao.getUser(username, password);
			if (u == null) {
				System.out.println(" UserName/Password does not exist ");
				session.setAttribute("errorMessage", "UserName/Password does not exist");				
				  response.setContentType("text/html;charset=utf-8");
		          PrintWriter out = response.getWriter();
		          out.print("<script type=\"text/javascript\"> "
		                  + "window.alert('Username/password does not exist.');"
		                  + "window.location.href='loginAgain.htm';"
		                  + "</script>");
		         			}
			session.setAttribute("user", u);			
			if (u.getRole().equals("admin")) {
				ModelAndView mv = new ModelAndView();				
				mv.setViewName("admin-menu");
				return mv;
			}

			else if (u.getRole().equals("professor")) {
				return new ModelAndView("professor-menu");
			}

			else{
				return new ModelAndView("student-menu");
			}		
	}
	
	
	
	@RequestMapping(value = "/userAccount", method = RequestMethod.GET)
	protected ModelAndView updateUserAccount() throws Exception {
		
		System.out.print(" jump to UpdateUserAccount page ");
		User user= new User();
		return new ModelAndView("userAccount","user",user);
	}
	
	
	@RequestMapping(value = "/userAccount", method = RequestMethod.POST)
	protected ModelAndView updateUserAccount(HttpServletRequest request,HttpServletResponse response, @ModelAttribute("user") User user) throws Exception {

		
		try {
			System.out.print("Update User " + user.getUsername());
			 if(!userDao.checkUserName(user.getUsername())){
				 
				 response.setContentType("text/html;charset=utf-8");
		          PrintWriter out = response.getWriter();
		          out.print("<script type=\"text/javascript\"> "
		                  + "window.alert('Username exist, please figure out a new username.');"
		                  + "window.location.href='register.htm';"
		                  + "</script>");
			 }
			HttpSession session = request.getSession();
	    	User currentUser = (User) session.getAttribute("user");
	    	System.out.print(" Get session user " + currentUser.getUsername());
							
			User u=userDao.getUserId(currentUser.getUserId());
			u.setUsername(request.getParameter("username"));

			u.setPassword(request.getParameter("password"));
			u.setEmail(request.getParameter("email"));
			
			userDao.update(u);
						
		    return new ModelAndView("register-success", "user", currentUser);

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("register-error", "errorMessage", "error while login");
		}
	}
	

	
}
