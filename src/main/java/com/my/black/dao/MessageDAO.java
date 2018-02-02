package com.my.black.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.black.exception.MessageException;

import com.my.black.pojo.Message;

public class MessageDAO extends DAO {
	public MessageDAO() {
	}

	// create message
	public Message createMessage(Message m) throws MessageException {
		try {
			begin();
			System.out.println("create message");
			getSession().save(m);
			commit();
			return m;
		} catch (HibernateException e) {
			rollback();
			throw new MessageException("Exception while creating message: " + e.getMessage());
		}
	}

	// getAll message
	public List<Message> getAllMessage() throws MessageException {

		try {
			begin();
			Query q = getSession().createQuery("from Message");
			List<Message> messageList = q.list();
			commit();
			return messageList;
		} catch (HibernateException e) {
			rollback();
			throw new MessageException("There is message in table");
		}
	}



	// Delete
	public void delete(Message m) throws MessageException {
		try {
			begin();
			getSession().delete(m);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new MessageException("Could not delete message: " + m.getMessageId(), e);
		}
	}

}
