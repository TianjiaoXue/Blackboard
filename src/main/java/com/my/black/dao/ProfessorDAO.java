package com.my.black.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.black.exception.ProfessorException;
import com.my.black.pojo.Course;
import com.my.black.pojo.Professor;
import com.my.black.pojo.User;



public class ProfessorDAO extends DAO {
	public ProfessorDAO(){
		
	}
	
	 public Professor create(Professor professor) throws ProfessorException {
	        try {
	            begin();
	            getSession().save(professor);
	            commit();
	            return professor;
	        } catch (HibernateException e) {
	            rollback();
	            
	            throw new ProfessorException("Exception while creating professor: " + e.getMessage());
	        }
	    }
	
	
	 public Professor getUserId(long userId) throws ProfessorException {
			try {
				begin();
				Query q = getSession().createQuery("from Professor where userId= :userId");
				q.setLong("userId", userId);		
				Professor p = (Professor) q.uniqueResult();
				commit();
				return p;
			} catch (HibernateException e) {
				rollback();
				throw new ProfessorException("Could not get professor " + userId, e);
			}
		}
	 
	public List<Professor> getProfessor() throws ProfessorException{
		try{
			begin();
			Query q = getSession().createQuery("from Professor");	
			List<Professor> professorList = q.list();//parse the object to string
			
			commit();
			return professorList;
		} catch (HibernateException e) {
			rollback();
			throw new ProfessorException("There is no professor in table");
		}
		
	}
	
	public List<Course> getProfessorCourse(Professor p) throws ProfessorException{
		try{
			begin();
			Query q = getSession().createQuery("from Course where professor_userId = :userId");	
			q.setLong("userId", p.getUserId());
			List<Course> professorCourse = q.list();//parse the object to string			
			commit();
			return professorCourse;
		} catch (HibernateException e) {
			rollback();
			throw new ProfessorException("There is no professor in table");
		}
		
	}
	
	
	 public void update(Professor p) throws ProfessorException {
	        try {
	            begin();
	            getSession().update(p);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new ProfessorException("Could not save the professor", e);
	        }
	    }
	 
	 public void delete(Professor professor) throws ProfessorException {
	        try {
	            begin();
	            getSession().delete(professor);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new ProfessorException("Could not save the professor", e);
	        }
	    }
	 
	 
}
