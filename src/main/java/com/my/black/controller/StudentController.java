package com.my.black.controller;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

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
import com.my.black.dao.StudentAssignmentDAO;
import com.my.black.dao.StudentDAO;
import com.my.black.dao.UserDAO;
import com.my.black.pojo.Assignment;
import com.my.black.pojo.Course;
import com.my.black.pojo.Professor;
import com.my.black.pojo.Student;
import com.my.black.pojo.StudentAssignment;

@Controller

public class StudentController {
	@Autowired
	@Qualifier("courseDao")
	CourseDAO courseDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;

	@Autowired
	@Qualifier("assignmentDao")
	AssignmentDAO assignmentDao;

	@Autowired
	@Qualifier("saDao")
	StudentAssignmentDAO saDao;

	@RequestMapping(value = "/student/chooseCourse", method = RequestMethod.GET)
	protected ModelAndView addCourse() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("courseList", courseDao.getAllCourse());
		mv.setViewName("student-chooseCourse");
		return mv;
	}

	@RequestMapping(value = "/student/chooseCourse", method = RequestMethod.POST)
	protected ModelAndView addCourse(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("course") Course course, BindingResult result) throws Exception {
		ModelAndView mv = new ModelAndView();
		String[] myCourse = request.getParameterValues("choose");

		HttpSession session1 = request.getSession();
		Student student = (Student) session1.getAttribute("user");
		long id = student.getUserId();

		Student s = studentDao.getStudentById(id);
		System.out.println(" student " + s.getUsername() + " choose " + myCourse.length + " courses ");

		for (int i = 0; i < myCourse.length; i++) {
			long courseId = Long.parseLong(myCourse[i]);
			Course c = courseDao.getCourseById(courseId);
			
			System.out.println("the " + i + " course name is " + c.getcourseTitle());
			s.getCourse().add(c);
			
			int total = s.getTotalCredit();
			total += c.getCredit();
            System.out.println("Student total credit : "+ total);
			if (total <13) {
				s.setTotalCredit(total);

				int currentAvail = c.getCapacity();
				currentAvail--;
				if (currentAvail > 0) {	
					System.out.println(" HHHH 1 ");
                    c.setCapacity(currentAvail);
                    s.setTotalCredit(total);
                    courseDao.update(c);
					studentDao.update(s);
					System.out.println("my course set" + s.getCourse());
					
					mv.addObject("myCourse",s.getCourse());
					mv.setViewName("student-viewCourse");	
					return mv;
					
									
				} else {
					System.out.println(" HHHH 2 ");
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					out.print("<script type=\"text/javascript\"> " + "window.alert('This course's capacity is full');"
							+ "window.location.href='chooseCourse.htm';" + "</script>");
				}
				
			} else {
				System.out.println(" HHHH 3 ");
				System.out.println("Total is more than 8 ,jump to here ");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print("<script type=\"text/javascript\"> "
						+ "window.alert('you cannot learn more than 8 total Score');"
						+ "window.location.href='viewCourse.htm';" + "</script>");
			}

		}
		return null;
	}

	@RequestMapping(value = "/student/viewCourse", method = RequestMethod.GET)
	public ModelAndView viewCourse(HttpServletRequest request) throws Exception {
		System.out.println(" jump to student view course page");

		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("user");

		//Set<Course> courseList = s.getCourse();
		List<Course> courseList=courseDao.getCourseByStudent(s);
		System.out.println("This student has total course " + courseList.size());

		mv.addObject("myCourse", courseList);
		mv.setViewName("student-viewCourse");
		return mv;
	}

	@RequestMapping(value = "/student/viewCourse", method = RequestMethod.POST)
	protected ModelAndView viewCourse(HttpServletRequest request, @ModelAttribute("course") Course course)
			throws Exception {
		ModelAndView mv = new ModelAndView();

		String[] myCourse = request.getParameterValues("choose");

		HttpSession session1 = request.getSession();
		Student student = (Student) session1.getAttribute("user");
		long id = student.getUserId();

		Student s = studentDao.getStudentById(id);

		System.out.println(myCourse.length + " Courses has been chosen to delete");
		for (int i = 0; i < myCourse.length; i++) {
			long courseId = Long.parseLong(myCourse[i]);

			Course c = courseDao.getCourseById(courseId);

			System.out.println(" student delete " + c.getcourseTitle() + " from his courseList");
			s.getCourse().remove(c);
			int curretTotal=s.getTotalCredit()-c.getCredit();
			s.setTotalCredit(curretTotal);
			int currentCapacity= c.getCapacity();
		    c.setCapacity(currentCapacity-1);
			studentDao.update(s);
			courseDao.update(c);
		}

		Set<Course> courseList = s.getCourse();
		System.out.println("This student has total course " + courseList.size());
		mv.addObject("myCourse", courseList);
		mv.setViewName("student-viewCourse");
		return mv;
	}

	
	@RequestMapping(value = "/student/selectCourseAssign", method = RequestMethod.GET)
	public ModelAndView selectCourseAssign(HttpServletRequest request) throws Exception {
		System.out.println(" jump tp student select course to do assignment page");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("user");
		mv.addObject("courseList", s.getCourse());
		mv.setViewName("student-selectCourseAssign");
		return mv;

	}

	@RequestMapping(value = "/student/selectCourseAssign", method = RequestMethod.POST)
	protected ModelAndView selectCourseAssign(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("assignment") Assignment assignment, BindingResult result) throws Exception {
		String courseName = request.getParameter("course");
		Course c = courseDao.getCourseByName(courseName);

		if (c.getAssignment()!= null) {
			System.out.print("selected course name: " + c.getcourseTitle());
			List<Assignment> myAssign = assignmentDao.getCourseAssignment(c);
			System.out.println("This course has " + myAssign.size() + " Assignments");
			return new ModelAndView("student-viewAssign", "assignmentList", myAssign);
		} else {
			System.out.print("selected course name: " + c.getcourseTitle() + " has no assignment");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\"> " + "window.alert('The course you choose have no assignment');"
					+ "window.location.href='selectCourseAssign.htm';" + "</script>");
		}
		return null;
	}

	@RequestMapping(value = "/student/uploadAssign", method = RequestMethod.GET)
	public ModelAndView viewAssign(HttpServletRequest request) throws Exception {
		System.out.println(" jump to student upload Assignment page");

		long id = Long.parseLong(request.getParameter("id"));

		System.out.print("selected assign name: " + id);
		Assignment a = assignmentDao.getAssginById(id);
		HttpSession session = request.getSession();
		session.setAttribute("assignment", a);
		return new ModelAndView("student-uploadAssign", "sa", new StudentAssignment());
	}

	@RequestMapping(value = "/student/uploadAssign", method = RequestMethod.POST)
	protected ModelAndView viewAssign(HttpServletRequest request, @ModelAttribute("sa") StudentAssignment sa)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		Student s = (Student) session.getAttribute("user");	
		Assignment a = (Assignment) session.getAttribute("assignment");
		
		sa.setStudent(s);
		sa.setAssignment(a);
		//String file = sa.getFile();
		
		Calendar ca = new GregorianCalendar();
		 Date uploadDate=ca.getTime();
		 if (uploadDate.compareTo(a.getDueDate()) > 0) {
			 System.out.println("uploadDate is after DueDate, this is a delay");
			 sa.setExpire(true);
		 }
		 else{
			 System.out.println("uploadDate is before DueDate, not delay");
			 sa.setExpire(false);
		 }
		
	    saDao.create(sa);
	    mv.addObject("courseList", s.getCourse());
		mv.setViewName("student-selectCourseAssign");
		return mv;
	}
	
	
	@RequestMapping(value = "/student/checkScore", method = RequestMethod.GET)
	public ModelAndView checkScore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" jump to student check Score page");

		long id = Long.parseLong(request.getParameter("id"));
		HttpSession session = request.getSession();
		Student st = (Student) session.getAttribute("user");
		
		Student s= studentDao.getStudentById(st.getUserId());
		Assignment a = assignmentDao.getAssginById(id);
		
		StudentAssignment sa=saDao.getSAByStudent(s,a);
		
		if(sa!=null){
		System.out.print(" Assign "+a.getAssignId() +" student saId "+ sa.getSaId());
		session.setAttribute("assignment", a);
		session.setAttribute("sa", sa);
		return new ModelAndView("student-checkScore");
		
		}else{
			System.out.print("you haven't upload the assignment yet");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\"> " + "window.alert('you haven't upload the assignment yet');"
					+ "window.location.href='selectCourseAssign.htm';" + "</script>");
		}
		return null;
	}

}
