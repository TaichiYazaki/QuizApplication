package com.example.domain;

public class Quiz {

	private Integer id;
	private String 	question;
	private Boolean answer;
	private String author;
	private Integer administratorId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Boolean getAnswer() {
		return answer;
	}
	public void setAnswer(Boolean answer) {
		this.answer = answer;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getAdministratorId() {
		return administratorId;
	}
	public void setAdministratorId(Integer administratorId) {
		this.administratorId = administratorId;
	}
	@Override
	public String toString() {
		return "Quiz [id=" + id + ", question=" + question + ", answer=" + answer + ", author=" + author
				+ ", administratorId=" + administratorId + "]";
	}
	
	
}
