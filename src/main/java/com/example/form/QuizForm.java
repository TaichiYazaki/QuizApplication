package com.example.form;

public class QuizForm {

	private Integer id;
	private String 	question;
	private Boolean answer;
	private String author;
	private Boolean newQuiz;
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
	public Boolean getNewQuiz() {
		return newQuiz;
	}
	public void setNewQuiz(Boolean newQuiz) {
		this.newQuiz = newQuiz;
	}
	@Override
	public String toString() {
		return "QuizForm [id=" + id + ", question=" + question + ", answer=" + answer + ", author=" + author
				+ ", newQuiz=" + newQuiz + "]";
	}
}
