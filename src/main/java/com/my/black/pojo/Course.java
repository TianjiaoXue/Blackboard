package com.my.black.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;



@Entity
@Table(name="course_table")
public class Course {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="courseId", unique = true, nullable = false)
	private long courseId;
	
	@Column(name="courseTitle")
	private String courseTitle;
	
	
	@Column(name="semester")
	private String semester;
	
	@Column(name="credit")
	private int credit;
	
	@Column(name="capacity")
	private int capacity;
	
	@ManyToOne
	private Professor professor;
	
	//course owns assignment
	@OneToMany(mappedBy = "course",cascade=CascadeType.ALL)
	private Set<Assignment> assignment = new HashSet<Assignment>();
	
	
	@ManyToMany(mappedBy="course",cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER)
	private Set<Student> student = new HashSet<Student>();
	
	public Course(){
		
	}
	
	public Course(String courseTitle, String semester, int credit, int capacity){
		this.courseTitle = courseTitle;
		this.semester = semester;
		this.credit=credit;
		this.capacity=capacity;
	}
	
	public Course(String courseTitle, String semester, int credit, int capacity, Set<Student> student, Professor professor){
		this.courseTitle = courseTitle;
		this.semester = semester;
		this.credit=credit;
		this.capacity=capacity;
		this.professor=professor;
		this.student=student;
	
	}
	
	public void addStudent(Student s){
		getStudent().add(s);
		s.getCourse().add(this);
	}

	public long getCourseId() {
		return courseId;
	}


	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}


	public String getcourseTitle() {
		return courseTitle;
	}


	public void setcourseTitle(String courseName) {
		this.courseTitle = courseName;
	}


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}

	

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Set<Student> getStudent() {
		return student;
	}

	public void setStudent(Set<Student> student) {
		this.student = student;
	}

	public Set<Assignment> getAssignment() {
		return assignment;
	}

	public void setAssignment(Set<Assignment> assignment) {
		this.assignment = assignment;
	}
	
	

}
