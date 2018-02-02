package com.my.black.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.black.exception.UserException;
import com.my.black.pojo.User;


public class UserDAO extends DAO {
	public UserDAO() {
	}
	
	//login by username and password
	public User getUser(String username, String password) throws UserException {
		try {
			begin();
			System.out.println("read from User table");
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get username and password " + username, e);
		}
	}
	
	public Boolean checkUserName(String username) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username= :username");
			q.setString("username", username);		
			User user = (User) q.uniqueResult();
			commit();
			System.out.println(username);
			if(user==null){
			   return true;
			}
			   else{return false;}
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}

	
	// Read from database by Id
	public User getUserId(long userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where userId= :userId");
			q.setLong("userId", userId);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + userId, e);
		}
	}
	
	// Read from database by username
		public User getUserName(String username) throws UserException {
			try {
				begin();
				Query q = getSession().createQuery("from User where username= :username");
				q.setString("username", username);		
				User user = (User) q.uniqueResult();
				commit();
				return user;
			} catch (HibernateException e) {
				rollback();
				throw new UserException("Could not get user " + username, e);
			}
		}
	
		// Read from database by username
				public User getByEmail(String email) throws UserException {
					try {
						begin();
						Query q = getSession().createQuery("from User where email= :email");
						q.setString("email", email);		
						User user = (User) q.uniqueResult();
						commit();
						return user;
					} catch (HibernateException e) {
						rollback();
						throw new UserException("Could not get user " + email, e);
					}
				}
			
	public List<User> getAllUser() throws UserException{
		
		try{
			begin();
			Query q= getSession().createQuery("from User");
			List<User> userList = q.list();
			commit();
			return userList;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("There is no user in table");
		}
		
		
	}
	
	//Create
	public User registerUser(User u) throws UserException{
		try {
			begin();
			System.out.println("registerUser");
			getSession().save(u);
			commit();
			return u;

		} catch (HibernateException e) {
			rollback();
		    throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}
    
	//Update
	 public void update(User user) throws UserException {
	        try {
	            begin();
	            
	            getSession().saveOrUpdate(user);
	            commit();
	        } catch (HibernateException e) {
	            rollback();
	            throw new UserException("Could not update the user: " + user.getUsername(), e);
	        }
	    }
	
	//Delete
	public void delete(User user) throws UserException {
		try {
			begin();
			getSession().delete(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not delete user: " + user.getUsername(), e);
		}
	}
	

}
