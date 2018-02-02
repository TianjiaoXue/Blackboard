package com.my.black.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.my.black.exception.CourseException;
import com.my.black.pojo.Course;
import com.my.black.pojo.Student;


public class CourseDAO extends DAO {
	public CourseDAO() {
	}

	
	public Course createCourse(Course c) throws CourseException{
		try{
			begin();
			getSession().save(c);
			commit();
			return c;
		} catch (HibernateException e) {
			rollback();
		    throw new CourseException("Exception while creating course: " + e.getMessage());
		}
	}
	
	public Course getCourseById(long id) throws CourseException {
		try {
			begin();
			Query q = getSession().createQuery("from Course where courseId= :courseId");
			q.setLong("courseId", id);		
			
			Course course= (Course) q.uniqueResult();
			commit();
			close();
	
			return course;
		} catch (HibernateException e) {
			rollback();
			throw new CourseException("Could not get user " , e);
		}
	}
	
	
	public boolean checkStudentCourseExist(Student s, Course c) throws CourseException {
		try {
			begin();
			Query q = getSession().createQuery("from Course c inner join Student s where c.courseId= :courseId and s.userId=:userId");
			q.setLong("courseId",c.getCourseId());		
			q.setLong("userId", s.getUserId());
			Course course= (Course) q.uniqueResult();
			commit();
			if(course==null){
				   return true;
				}
				   else{return false;}
		} catch (HibernateException e) {
			rollback();
			throw new CourseException("unable to check if exist " , e);
		}
	}
	
	public List<Course> getCourseByStudent(Student s) throws CourseException {
		try {
			begin();
			Criteria crit = getSession().createCriteria(Course.class);
			crit.createAlias("student", "s");
			crit.add(Restrictions.eq("s.userId", s.getUserId()));
			List<Course> course=crit.list();
			commit();
			close();
	
			return course;
		} catch (HibernateException e) {
			rollback();
			throw new CourseException("Could not get course " , e);
		}
	}
	
	public Course getCourseByName(String courseTitle) throws CourseException {
		try {
			begin();
			Query q = getSession().createQuery("from Course where courseTitle= :courseTitle");
			q.setString("courseTitle",courseTitle);					
			Course course= (Course) q.uniqueResult();
			commit();
			return course;
		} catch (HibernateException e) {
			rollback();
			throw new CourseException("Could not get course " , e);
		}
	}
	
	
	
	 public List<Course> getAllCourse() throws CourseException{  	
	    	try {
	            begin();
	            Query q = getSession().createQuery("from Course");
	            List<Course> courseList = q.list();
	            commit();
	            return courseList;
	        } catch (HibernateException e) {
	            rollback();
	            throw new CourseException("Could not delete course", e);
	        }
	    }   	
	 
	 
	public List<Course> searchCourse(String keyword, String searchType) throws CourseException {
		try {
			begin();
			System.out.println("search course table");

			if (searchType.equals("courseTitle")) {
				Query q = getSession().createQuery("from Course where courseTitle = :courseTitle");
				q.setString("courseTitle", keyword);	
				List<Course> courseList = q.list();
				commit();
				return courseList;
			}

			if (searchType.equals("semseter")) {
				Query q = getSession().createQuery("from Course where semester = :semester");
				q.setString("semester", keyword);
				List<Course> courseList = q.list();
				commit();
				return courseList;
			}
			
//			if (searchType.equals("professor")) {
//				Query q = getSession().createQuery("from Course where userId = :professorId");
//				
//				q.setString("professrName", keyword);
//				List<Course> courseList = q.list();
//				commit();
//				return courseList;
//			}

		} catch (HibernateException e) {
			rollback();
			throw new CourseException("Could not get course ", e);
		}
		return null;
	}
	
	
	 public void update(Course course) throws CourseException {
	        try {
	            begin();
	            getSession().update(course);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new CourseException("Could not update the course: " + course.getcourseTitle(), e);
	        }
	    }
	 
	
	
	public void delete(Course course) throws CourseException {
		try {
			begin();
			getSession().delete(course);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new CourseException("Could not delete course: " , e);
		}
	}
	
	 
    
    

}
