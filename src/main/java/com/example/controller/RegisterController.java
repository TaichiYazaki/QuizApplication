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

	/*
	 * アカウントの登録
	 */
	@RequestMapping("/insert")
	public String insert(RegisterForm registerForm, Model model) {

		// 空欄で検索した場合 & emailが既に登録されている時の処理
		if (registerForm.getName().isEmpty()
				|| registerForm.getEmail().isEmpty()
				|| registerForm.getPassword().isEmpty()
				|| registerForm.getConfirmPassword().isEmpty()) {
			model.addAttribute("blanksMsg", "Please fill in the blanks...");
			return "register";
		} else if (!(registerService.findEmail(registerForm.getEmail()).isEmpty())) {
			model.addAttribute("emailMsg", "email already in use...");
			return "register";
		} else if (!(registerForm.getPassword().equals(registerForm.getConfirmPassword()))) {
			model.addAttribute("passwordMsg", "password and confirmPassword are incorect...");
			return "register";
		}
		// emailがDBにまだ登録されていない時の処理
		Register register = new Register();
		register.setName(registerForm.getName());
		register.setEmail(registerForm.getEmail());
		register.setPassword(registerForm.getPassword());
		register.setConfirmPassword(registerForm.getConfirmPassword());
		registerService.insert(register);
		// idの取得
		Register registerId = registerService.findAccount(registerForm.getEmail(), registerForm.getPassword());
		session.setAttribute("session", registerId);
		return "redirect:/quiz/myList?id=" + registerId.getId();
	}

	/*
	 * ログイン画面の表示
	 */
	@RequestMapping("/loginMenu")
	public String loginMenu() {
		return "/login";
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