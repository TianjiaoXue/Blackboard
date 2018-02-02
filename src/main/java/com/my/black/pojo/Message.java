package com.my.black.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="message_table")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="messageId", unique = true, nullable = false)
    private long messageId;
	
	@Column(name="content")
	private String content;
	
    
    @Column(name = "userId")
    private String sender;

	
	@Column(name="sendTime")
	private Date sendTime;
	
	@Column(name="print")
	private boolean print;
	



	public Message(){
		
	}
	
	
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public Date getSendTime() {
		return sendTime;
	}


	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}



	public boolean isPrint() {
		return print;
	}


	public void setPrint(boolean print) {
		this.print = print;
	}





	

}
