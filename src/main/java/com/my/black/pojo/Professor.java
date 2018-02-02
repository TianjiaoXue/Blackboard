package com.my.black.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="professor_table")
@PrimaryKeyJoinColumn(name="userId")
public class Professor extends User{
	
	@Column(name="totalCourse")
	private int totalCourse;
	
	@OneToMany(mappedBy = "professor",cascade=CascadeType.ALL)
	private Set<Course> course = new HashSet<Course>();
	
	public Professor(){
		
	}
	
	public Professor(int totalCourse){
		this.totalCourse=totalCourse;
	}
	
	
	public Set<Course> getCourse() {
		return course;
	}

	public void setCourse(Set<Course> course) {
		this.course = course;
	}
	public int getTotalCourse() {
		return totalCourse;
	}
	public void setTotalCourse(int totalCourse) {
		this.totalCourse = totalCourse;
	}
	
	


}
