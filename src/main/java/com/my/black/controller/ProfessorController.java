package com.my.black.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.my.black.pojo.Assignment;
import com.my.black.pojo.Course;
import com.my.black.pojo.Professor;
import com.my.black.pojo.Student;
import com.my.black.pojo.StudentAssignment;



@Controller
@RequestMapping("/professor/*")
public class ProfessorController {

	@Autowired
	@Qualifier("courseDao")
	CourseDAO courseDao;

	@Autowired
	@Qualifier("professorDao")
	ProfessorDAO professorDao;
	
	
	@Autowired
	@Qualifier("assignmentDao")
	AssignmentDAO assignmentDao;
	
	@Autowired
	@Qualifier("saDao")
	StudentAssignmentDAO saDao;

	@RequestMapping(value = "/professor/addCourse", method = RequestMethod.GET)
	public ModelAndView teachCourse(HttpServletRequest request) throws Exception {
		System.out.println(" jump tp professor-course page");

		List<Course> courseList = courseDao.getAllCourse();
		return new ModelAndView("professor-addCourse", "courseList", courseList);

	}

	@RequestMapping(value = "/professor/addCourse", method = RequestMethod.POST)
	protected ModelAndView teachCourse(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("course") Course course,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<Course> courseList = courseDao.getAllCourse();
			return new ModelAndView("professor-addCourse", "courseList", courseList);
		}

			String[] myCourse = request.getParameterValues("choose");
			System.out.println(" Professor choose "+ myCourse.length+ " new courses ");

			HttpSession session = request.getSession();
			Professor p = (Professor) session.getAttribute("user");

			for (int i = 0; i < myCourse.length; i++) {
				long courseId = Long.parseLong(myCourse[i]);
				Course c = courseDao.getCourseById(courseId);
				
				if (c.getProfessor()==null) {	
					//check if the course has been choosed by a professor
					
					List<Course> pCourseList=professorDao.getProfessorCourse(p);
					int myTeach = pCourseList.size();
					System.out.println("professor has course number: "+ myTeach);
										
					if(myTeach<3){
						c.setProfessor(p);
						courseDao.update(c);
						myTeach++;
						p.setTotalCourse(myTeach);
						
						System.out.print(" professor choose course " + c.getcourseTitle());
						return new ModelAndView("professor-menu");
					}else{
						System.out.print(" professor cannot choose more than three course ");
						response.setContentType("text/html;charset=utf-8");
		                PrintWriter out = response.getWriter();
		                out.print("<script type=\"text/javascript\"> "
		                        + "window.alert('You will not allow to choose more than three course.');"
		                        + "window.location.href='addCourse.htm';"
		                        + "</script>");
					}
					
				} else {
					System.out.print(" the course---" + c.getcourseTitle() + " has been choosed by other professor ");
					response.setContentType("text/html;charset=utf-8");
	                PrintWriter out = response.getWriter();
	                out.print("<script type=\"text/javascript\"> "
	                        + "window.alert('The course has been chosen by other professor.');"
	                        + "window.location.href='addCourse.htm';"
	                        + "</script>");					
				    }
				
				
			}
	
			return null;
	}


	@RequestMapping(value = "/professor/viewCourse", method = RequestMethod.GET)
	public ModelAndView viewProfessorCourse(HttpServletRequest request) throws Exception {
		System.out.println(" jump to professor view page");
		
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = request.getSession();
		Professor p = (Professor) session.getAttribute("user");
		List<Course> courseList =professorDao.getProfessorCourse(p);
		
		System.out.println("This professor has total course "+ courseList.size());
		
		mv.addObject("courseList",courseList);			
		mv.setViewName("professor-viewCourse");
		return mv;
	}
	
	
	@RequestMapping(value = "/professor/viewCourse", method = RequestMethod.POST)
	protected ModelAndView viewCourse(HttpServletRequest request, @ModelAttribute("course") Course course) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		String[] myCourse = request.getParameterValues("choose");
		
		System.out.println(myCourse.length +" Courses has been chosen to delete");
		for (int i = 0; i < myCourse.length; i++) {
			long courseId = Long.parseLong(myCourse[i]);
			Course c = courseDao.getCourseById(courseId);
			System.out.println(" professor delete "+c.getcourseTitle() + " from his courseList");
            c.setProfessor(null);
            courseDao.update(c);
		}
		
		HttpSession session = request.getSession();
		Professor p = (Professor) session.getAttribute("user");
		List<Course> courseList =professorDao.getProfessorCourse(p);
		mv.addObject("courseList",courseList);			
		mv.setViewName("professor-viewCourse");
		return mv;
	}
	
	
	@RequestMapping(value = "/professor/assignment", method = RequestMethod.GET)
	public ModelAndView postAssignment(HttpServletRequest request) throws Exception {
		System.out.println(" jump tp professor-assignment page");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Professor p = (Professor) session.getAttribute("user");	
		List<Course> courseList=professorDao.getProfessorCourse(p);	
		mv.addObject("courseList",courseList);			
		mv.addObject("assignment", new Assignment());
		mv.setViewName("professor-assign");
		return mv;

	}
	

	@RequestMapping(value = "/professor/assignment", method = RequestMethod.POST)
	protected ModelAndView addAssignment(HttpServletRequest request, @ModelAttribute("assignment") Assignment assignment,
			BindingResult result) throws Exception {

			System.out.print("create assignment" + assignment.getAssignId());
		    String courseName=request.getParameter("course");
		    Course c= courseDao.getCourseByName(courseName);
		    assignment.setCourse(c);
		   
		    Calendar ca=new GregorianCalendar();
		    ca.add(Calendar.DATE, 7);
		    Date dueDate=ca.getTime();
            //due date is a week after current date
		    System.out.println("dueDate time is: "+ dueDate);
			assignment.setDueDate(dueDate);
			assignmentDao.create(assignment);
		    
			return new ModelAndView("professor-menu", "assignment", assignment);		
	}
	

	@RequestMapping(value = "/professor/selectCourseAssign", method = RequestMethod.GET)
	public ModelAndView chooseCourseToViewAssignment(HttpServletRequest request) throws Exception {
		System.out.println(" jump tp professor-viewAssign page");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Professor p = (Professor) session.getAttribute("user");	
		List<Course> courseList=professorDao.getProfessorCourse(p);	
		mv.addObject("courseList",courseList);			
		mv.setViewName("professor-selectCourseAssign");
		return mv;
	}
	
	@RequestMapping(value = "/professor/selectCourseAssign", method = RequestMethod.POST)
	protected ModelAndView chooseCourseToViewAssignment(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("assignment") Assignment assignment, BindingResult result) throws Exception {
		String courseName = request.getParameter("course");
		Course c = courseDao.getCourseByName(courseName);

		if (c.getAssignment().size()!=0) {
			System.out.print("selected course name: " + c.getcourseTitle());
			List<Assignment> myAssign = assignmentDao.getCourseAssignment(c);
			System.out.println("This course has " + myAssign.size() + " Assignments");
			return new ModelAndView("professor-viewAssign", "assignmentList", myAssign);
		} else {
			System.out.print("selected course name: " + c.getcourseTitle() + " has no assignment");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\"> " + "window.alert('The course you choose have no assignment');"
					+ "window.location.href='selectCourseAssign.htm';" + "</script>");
		}
		return null;
	}
	
	
	@RequestMapping(value = "/professor/scoreAssign", method = RequestMethod.GET)
	public ModelAndView scoreAssign(HttpServletRequest request) throws Exception {
		System.out.println(" jump to professr score Assignment page");

		long id = Long.parseLong(request.getParameter("id"));

		System.out.print("selected assign name: " + id);
		Assignment a = assignmentDao.getAssginById(id);
		List<StudentAssignment> saList= saDao.getAssignmentForOneTime(a);
		System.out.println(saList.size()+ " assignment has been upload");
		HttpSession session = request.getSession();
		session.setAttribute("assignment", a);
		return new ModelAndView("professor-scoreAssign", "saList", saList);
	}
	
	
	@RequestMapping(value = "/professor/scoreAssign", method = RequestMethod.POST)
	protected ModelAndView scoreAssign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String[] scores = request.getParameterValues("scores");
		String[] saIds=request.getParameterValues("saId");
		
		for (int i = 0; i < scores.length; i++) {
			int score=Integer.parseInt(scores[i]);
            long saId = Long.parseLong(saIds[i]);
			System.out.println("update assignment "+ saId);
			StudentAssignment sa=saDao.getSAById(saId);
			sa.setScore(score);
			System.out.println(i+ "th assignment course is "+sa.getScore());
			saDao.update(sa);
		}
		HttpSession session = request.getSession();
		Professor p = (Professor) session.getAttribute("user");	
		List<Course> courseList=professorDao.getProfessorCourse(p);	
		mv.addObject("courseList",courseList);	
		mv.setViewName("professor-selectCourseAssign");
		return mv;
	}
	
	
}
