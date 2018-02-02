package com.my.black.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="student_table")
@PrimaryKeyJoinColumn(name="userId")
public class Student extends User {
	
	@Column(name="totalCredit")
	private int totalCredit;
	
	@Column(name="grade")
	private double grade;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="student_course_table",
	joinColumns = {@JoinColumn(name="userId")},
	inverseJoinColumns = {@JoinColumn(name="courseId" )})
	private Set<Course> course = new HashSet<Course>();
	
	@OneToMany(mappedBy = "assignment",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private Set<StudentAssignment> student = new HashSet<StudentAssignment>();
	
	public Student(){
		
	}
	
	public Student(int totalCredit, double grade){
		this.totalCredit=totalCredit;
		this.grade=grade;
	}
	
	public Student(int totalCredit, double grade, Set<Course> course){
		this.totalCredit=totalCredit;
		this.grade=grade;
		this.course=course;
	}
	public int getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(int totalCredit) {
		this.totalCredit = totalCredit;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public Set<Course> getCourse() {
		return course;
	}

	public void setCourse(Set<Course> course) {
		this.course = course;
	}
	
}
