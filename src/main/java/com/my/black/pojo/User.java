package com.my.black.pojo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name="user_table")
@Inheritance(strategy= InheritanceType.JOINED)
public class User {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId", unique = true, nullable = false)
	private long userId;
	
	@Column(name="username", unique=true, nullable = false)
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email",unique = true)
	private String email;
	
	@Column(name="role")
	private String role;
	
	public User(){
		//empty constructor
	}
	
	public User(String username, String password, String email, String role){
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
}
