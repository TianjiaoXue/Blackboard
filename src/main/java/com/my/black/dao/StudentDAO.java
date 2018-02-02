package com.my.black.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.black.exception.StudentException;
import com.my.black.pojo.Course;
import com.my.black.pojo.Professor;
import com.my.black.pojo.Student;

public class StudentDAO extends DAO{
	
	 public Student create(Student s) throws StudentException {
	        try {
	            begin();
	            getSession().save(s);
	            commit();
	            return s;
	        } catch (HibernateException e) {
	            rollback();
	           
	            throw new StudentException("Exception while creating Student: " + e.getMessage());
	        }
	    }
	 	
	
	 public Student getStudentById(long userId) throws StudentException {
			try {
				begin();
				Query q = getSession().createQuery("from Student where userId= :userId");
				q.setLong("userId", userId);		
				Student s = (Student) q.uniqueResult();
				commit();
				return s;
			} catch (HibernateException e) {
				rollback();
				throw new StudentException("Could not get student " + userId, e);
			}
		}

	public List<Student> getStudent() throws StudentException{
		try{
			begin();
			Query q = getSession().createQuery("from Student");	
			List<Student> studentList = q.list();
			
			commit();
			return studentList;
		} catch (HibernateException e) {
			rollback();
			throw new StudentException("There is no Student in table");
		}
		
	}

	
	 public void update(Student s) throws StudentException {
	        try {
	            begin();
	            getSession().update(s);
	            getSession().contains(null);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new StudentException("Could not save the Student", e);
	        }
	    }
	 
	 public void delete(Student s) throws StudentException {
	        try {
	            begin();
	            getSession().delete(s);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new StudentException("Could not save the Student", e);
	        }
	    }
	 

}
