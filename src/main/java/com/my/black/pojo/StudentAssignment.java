package com.my.black.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_assignment")
public class StudentAssignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="saId", unique = true, nullable = false)
	private long saId;
	
	

		@ManyToOne
	    private Student student;

	    @ManyToOne
	    private Assignment assignment;

	    @Column(name = "score")
	    private int score;
	    
	    @Column(name = "file")
	    private String file;
	    
	    
	    @Column(name = "expire")
	    private boolean expire;

	    public void setSaId(long saId) {
			this.saId = saId;
		}

			public long getSaId() {
				return saId;
			}

		public Student getStudent() {
			return student;
		}


		public void setStudent(Student student) {
			this.student = student;
		}


		public Assignment getAssignment() {
			return assignment;
		}


		public void setAssignment(Assignment assignment) {
			this.assignment = assignment;
		}


		public int getScore() {
			return score;
		}


		public void setScore(int score) {
			this.score = score;
		}


		public String getFile() {
			return file;
		}


		public void setFile(String file) {
			this.file = file;
		}


		public boolean isExpire() {
			return expire;
		}


		public void setExpire(boolean expire) {
			this.expire = expire;
		}
	       

}
