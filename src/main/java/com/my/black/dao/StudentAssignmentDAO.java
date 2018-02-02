package com.my.black.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.black.exception.SAException;
import com.my.black.pojo.Assignment;
import com.my.black.pojo.Course;
import com.my.black.pojo.Student;
import com.my.black.pojo.StudentAssignment;

public class StudentAssignmentDAO extends DAO {
	public StudentAssignmentDAO() {
	}
	
	public StudentAssignment create(StudentAssignment as) throws SAException{
		try{
			begin();
			getSession().save(as);
			commit();
			return as;
		} catch (HibernateException e) {
			rollback();
		    throw new SAException("Exception while creating assignment: " + e.getMessage());
		}		
	}
	
	public List<StudentAssignment> getAssignmentForOneTime(Assignment assignment) throws SAException{  	
    	try {
            begin();
            Query q = getSession().createQuery("from StudentAssignment sa where sa.assignment.assignId =:assignId");
            q.setLong("assignId", assignment.getAssignId());
            List<StudentAssignment> assignList = q.list();
            commit();
            return assignList;
        } catch (HibernateException e) {
            rollback();
            throw new SAException("Could not get Assignment", e);
        }
    }   
	
	
	public StudentAssignment getSAById(long saId) throws SAException{  	
    	try {
            begin();
            Query q = getSession().createQuery("from StudentAssignment where saId =:saId");
            q.setLong("saId",saId);
            StudentAssignment sa= (StudentAssignment) q.uniqueResult();
            commit();
            return sa;
        } catch (HibernateException e) {
            rollback();
            throw new SAException("Could not get Assignment", e);
        }
    }  
	
	
	public StudentAssignment getSAByStudent(Student s, Assignment a) throws SAException{  	
    	try {
            begin();
            Query q = getSession().createQuery("from StudentAssignment sa where sa.student.userId =:userId and sa.assignment.assignId =:assignId ");
            q.setLong("userId",s.getUserId());
            q.setLong("assignId", a.getAssignId());
            StudentAssignment sa= (StudentAssignment) q.uniqueResult();
            commit();
            return sa;
        } catch (HibernateException e) {
            rollback();
            throw new SAException("Could not get Assignment", e);
        }
    }  
	 
	
	
	 public void update(StudentAssignment a) throws SAException {
	        try {
	            begin();
	            getSession().update(a);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new SAException("Could not update your assignment ");
	        }
	    }
	 
	
	public void delete(Assignment a) throws SAException {
		try {
			begin();
			getSession().delete(a);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new SAException("Could not delete assignment: " , e);
		}
	}
	

}
