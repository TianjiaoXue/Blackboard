package com.my.black.pojo;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="assignment_table")
public class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="assignId", unique = true, nullable = false)
    private long assignId;
	
	@Column(name="score", nullable = false)
	private int score;
	
	@Column(name="dueDate", nullable = false)
	private Date dueDate;
	
	@Column(name="description", nullable = false)
	private String description;
	
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	private Course course; 
	
	@OneToMany(mappedBy = "assignment",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private Set<StudentAssignment> student = new HashSet<StudentAssignment>();
    
    
	
	public Assignment(){
		
	}

	public long getAssignId() {
		return assignId;
	}

	public void setAssignId(long assignId) {
		this.assignId = assignId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	

}
