package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Quiz;
import com.example.form.QuizForm;
import com.example.form.RegisterForm;
import com.example.service.QuizService;
import com.example.service.RegisterService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	public QuizService quizService;
	
	@Autowired
	public RegisterService registerService;

	@ModelAttribute
	public QuizForm setUpQuizForm() {
		return new QuizForm();
	}

	@ModelAttribute
	public RegisterForm setUpRegisterForm() {
		return new RegisterForm();
	}
	/*
	 * クイズ一覧を表示させる機能(全件)
	 */
	@RequestMapping("/list")
	public String list(QuizForm qform, Model model, RegisterForm rform) {
		qform.setNewQuiz(true);
		List<Quiz> list = quizService.list();
		model.addAttribute("id",qform.getId());
		model.addAttribute("list", list);
		model.addAttribute("title", "Register Form");
		model.addAttribute("quizForm", qform);
		return "allLists";
	}
	/*
	 * クイズの一覧表示(自分の投稿のみ)
	 */
	@RequestMapping("/myList")
	public String myList(RegisterForm rform, Model model,QuizForm qform) {
		qform.setNewQuiz(true);
		List<Quiz> myList = quizService.myList(rform.getId());
		model.addAttribute("id",qform.getId());
		model.addAttribute("myList", myList);
		model.addAttribute("title", "Register Form");
		model.addAttribute("quizForm", qform);
		return "quiz";
	}

	/*
	 * クイズを新規登録する処理
	 */
	@RequestMapping("/insert")
	public String insert(QuizForm quizForm, RegisterForm registerForm,Model model) {
		Quiz quiz = new Quiz();
		if(quizForm.getQuestion().isEmpty() || quizForm.getAnswer()==null || quizForm.getAuthor().isEmpty()) {
			model.addAttribute("PleaseFillInTheBlanks", "Please fill in the blanks");
			return "forward:/quiz/myList?id="+registerForm.getId();
		}else {
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		quiz.setAdministratorId(registerForm.getId());
		quizService.insert(quiz);
		return "redirect:/quiz/myList?id="+registerForm.getId();
		}
	}
	/*
	 * クイズを編集する処理
	 * QuizForm　form →　QuizForm quizFormへ変更
	 */
	@RequestMapping("/detail")
	public String detail(QuizForm quizForm,Model model) {
		quizForm.setNewQuiz(false);
		System.out.println(quizForm);
		Quiz quiz = quizService.load(quizForm.getId());
		model.addAttribute("id", quizForm.getId());
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("myList", quiz);
		model.addAttribute("title", "Update Form");
		return "quiz";
	}

	/*
	 * クイズの更新処理
	 */
	@RequestMapping("/update")
	public String update(QuizForm quizForm,Model model) {
		if(quizForm.getQuestion().isEmpty() || quizForm.getAnswer()==null || quizForm.getAuthor().isEmpty()) {
			model.addAttribute("PleaseFillInTheBlanks", "Please Fill In The Blanks");
			return "forward:/quiz/detail?id="+quizForm.getId();
		}
		Quiz quiz = quizService.load(quizForm.getId());
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());

		quizService.update(quiz);
		return "redirect:/quiz/myList?id="+quiz.getAdministratorId();
	}

	/*
	 * クイズの削除処理
	 */
	
	@RequestMapping("/delete")
	public String delete(QuizForm quizForm) {
		Quiz quiz=quizService.load(quizForm.getId());
		quizService.delete(quizForm.getId());
		return "redirect:/quiz/myList?id="+quiz.getAdministratorId();
	}

	/*
	 * クイズをランダムで取得
	 */
	@RequestMapping("/play")
	public String showRandom(Model model, RegisterForm registerForm) {
		List<Quiz> random = quizService.random();
		model.addAttribute("random", random);
		model.addAttribute("id",registerForm.getId());
		return "play";
	}

	/*
	 * 解答の判定
	 */
	@RequestMapping("/check")
	public String check(QuizForm form, RegisterForm registerForm, Model model, Boolean answer) {
		if (quizService.check(form.getId(), answer)) {
			model.addAttribute("msg", "True!!!");
		} else {
			model.addAttribute("msg", "False・・・");
		}
		model.addAttribute("id", (registerForm.getId()));
		return "answer";
	}
}
