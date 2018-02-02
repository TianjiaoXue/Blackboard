package com.my.black.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.black.exception.AssignmentException;
import com.my.black.exception.UserException;
import com.my.black.pojo.Assignment;
import com.my.black.pojo.Course;
import com.my.black.pojo.User;



public class AssignmentDAO extends DAO {
	public AssignmentDAO() {
	}
	
	public Assignment create(Assignment a) throws AssignmentException{
		try{
			begin();
			getSession().save(a);
			commit();
			return a;
		} catch (HibernateException e) {
			rollback();
		    throw new AssignmentException("Exception while creating assignment: " + e.getMessage());
		}
		
	}
	public Assignment getAssginById(long assignId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from Assignment where assignId = :assignId");
			q.setLong("assignId", assignId);		
			Assignment a = (Assignment) q.uniqueResult();
			commit();
			return a;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get Assignment " + assignId, e);
		}
	}
	
	 public List<Assignment> getAllAssignment() throws AssignmentException{  	
	    	try {
	            begin();
	            Query q = getSession().createQuery("from Assignment");
	            List<Assignment> assignList = q.list();
	            commit();
	            return assignList;
	        } catch (HibernateException e) {
	            rollback();
	            throw new AssignmentException("Could not get Assignment", e);
	        }
	    }   
	
	 public List<Assignment> getCourseAssignment(Course course) throws AssignmentException{  	
	    	try {
	            begin();
	            Query q = getSession().createQuery("from Assignment a where a.course.courseId = :courseId");
	            q.setParameter("courseId",course.getCourseId());
	           
	            List<Assignment> assignList = q.list();
	            commit();
	            return assignList;
	        } catch (HibernateException e) {
	            rollback();
	            throw new AssignmentException("Could not get Assignment", e);
	        }
	    }  
	 public void update(Assignment a) throws AssignmentException {
	        try {
	            begin();
	            getSession().update(a);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new AssignmentException("Could not update the assignment on course " + a.getCourse().getcourseTitle(), e);
	        }
	    }
	 
	 
	
	public void delete(Assignment a) throws AssignmentException {
		try {
			begin();
			getSession().delete(a);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new AssignmentException("Could not delete assignment: " , e);
		}
	}
	
	}