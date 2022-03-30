package com.example.domain;

public class JoinRegisterAndQuiz {

	private Integer id;
	private String name;
	private String email;
	private String password;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
		return "JoinRegisterAndQuiz [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", question=" + question + ", answer=" + answer + ", author=" + author + ", administratorId="
				+ administratorId + "]";
	}
	
	
	
}
