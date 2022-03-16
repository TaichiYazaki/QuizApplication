package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Quiz;
import com.example.form.QuizForm;
import com.example.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	public QuizService quizService;

	@ModelAttribute
	public QuizForm setUpQuizForm() {
		return new QuizForm();
	}

	/*
	 * クイズの一覧を表示させる機能()
	 */
	@RequestMapping("/list")
	public String list(QuizForm form, Model model) {
		form.setNewQuiz(true);
		List<Quiz> list = quizService.list();
		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		model.addAttribute("quizForm", form);
		return "quiz";
	}

	/*
	 * クイズを新規登録する処理
	 */
	@RequestMapping("/insert")
	public String insert(QuizForm form) {
		Quiz quiz = new Quiz();
		quiz.setQuestion(form.getQuestion());
		quiz.setAnswer(form.getAnswer());
		quiz.setAuthor(form.getAuthor());
		quizService.insert(quiz);
		return "redirect:/quiz/list";
	}

	/*
	 * クイズを編集する処理
	 */
	@RequestMapping("/detail")
	public String detail(String id, Model model, QuizForm form) {
		model.addAttribute("id", form.getId());
		form.setNewQuiz(false);
		int numId = Integer.parseInt(id);
		Quiz quiz = quizService.load(numId);
		model.addAttribute("quizForm", form);
		model.addAttribute("list", quiz);
		model.addAttribute("title", "更新用フォーム");
		return "quiz";
	}

	/*
	 * クイズの更新処理
	 */
	@RequestMapping("/update")
	public String update(QuizForm form) {
		Quiz quiz = quizService.load(form.getId());
		quiz.setQuestion(form.getQuestion());
		quiz.setAnswer(form.getAnswer());
		quiz.setAuthor(form.getAuthor());
		System.out.println(quiz);
		quizService.update(quiz);
		return "redirect:/quiz/list";
	}

	/*
	 * クイズの削除処理
	 */
	@RequestMapping("/delete")
	public String delete(String id) {
		quizService.delete(Integer.parseInt(id));
		return "redirect:/quiz/list";

	}
}
