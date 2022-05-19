package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.LoginUser;
import com.example.domain.Register;
import com.example.form.RegisterForm;
import com.example.service.RegisterService;

@Controller
@RequestMapping("/administrator")
public class RegisterController {

	// セッションを使うための設定
	@Autowired
	private HttpSession session;
	@Autowired
	public RegisterService registerService;

	@ModelAttribute
	private RegisterForm setUpRegisterForm() {
		return new RegisterForm();
	}

	/*
	 * 登録画面の表示
	 */
	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	/**
	 * 
	 * アカウントを登録する機能
	 */
	@RequestMapping("/insert")
	public String insert(RegisterForm form, Model model) {
		if (form.getName().isEmpty() || form.getEmail().isEmpty() || form.getPassword().isEmpty()) {
			return "redirect:/administrator/register";
		} else {

			Register register = new Register();
			register.setName(form.getName());
			register.setEmail(form.getEmail());
			register.setPassword(form.getPassword());
			registerService.insert(register);
			model.addAttribute("msg1", "Complete to register your account!!");
			model.addAttribute("msg2", "Please log in!!");
			return "forward:/administrator/loginMenu";
		}
	}

	/*
	 * ログイン画面の表示
	 */
	@RequestMapping("/loginMenu")
	public String loginMenu() {
		return "/login";
	}

	/**
	 * ログアウト機能
	 */
	@RequestMapping("/logout")
	public String logout() {
		return "redirect:/administrator/loginMenu";
	}

	/*
	 * ログイン処理
	 */
	@RequestMapping("/login")
	public String login(RegisterForm form, Model model, LoginUser loginUser) {
		Register register = registerService.findAccount(form.getEmail(), form.getPassword());

		if (register == null) {
			model.addAttribute("msg", "Not Collect email or password...");
			return "forward:/administrator/loginMenu";
		} else {
			model.addAttribute("register", register);
			session.setAttribute("session", register);
			return "redirect:/quiz/myList";

		}
	}

	/*
	 * パスワード重複確認
	 */
	@ResponseBody
	@RequestMapping(value = "/passwordCheck", method = RequestMethod.POST)
	public Map<String, String> passwordCheck(String password, String confirmPassword) {
		Map<String, String> map = new HashMap<>();
		String duplicateMessage = null;
		if (confirmPassword.equals(password)) {
			duplicateMessage = "Collect Password!";
		} else {
			duplicateMessage = "It doesn't match password...";

		}
		map.put("duplicateMessage", duplicateMessage);
		return map;
	}
}