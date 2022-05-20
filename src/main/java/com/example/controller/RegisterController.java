package com.example.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Register;
import com.example.form.RegisterForm;
import com.example.service.RegisterService;

@Controller
@RequestMapping("/administrator")
public class RegisterController {

	@Autowired
	public RegisterService registerService;

	@ModelAttribute
	private RegisterForm setUpRegisterForm() {
		return new RegisterForm();
	}

	/** 
	 * 登録画面の表示
	 * 
	 */
	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	/**
	 *  アカウントをDBへ登録
	 *
	 */
	@RequestMapping("/insert")
	public String insert(RegisterForm form, String confirmPassword, Model model) {

		//名前、メールアドレス、パスワードのどれかが空欄で送信されたら登録画面を再度表示
		if (form.getName().isEmpty() || form.getEmail().isEmpty() || form.getPassword().isEmpty()) {
			model.addAttribute("msg", "Please fill in the blanks...");
			return "forward:/administrator/register";
		//パスワードと確認用パスワードが一致しなければ、登録画面を再度表示
		} else if (!(form.getPassword().equals(confirmPassword))) {
			model.addAttribute("msg", "Not collect password and confirmPassword...");
			return "forward:/administrator/register";
		//メールアドレスが既にDBへ登録されていれば、登録画面を再度表示
		} else if(! registerService.findEmail(form.getEmail()).isEmpty()){
			model.addAttribute("msg", "Already exist this email...");
			return "forward:/administrator/register";
		//DBへアカウント情報を登録する処理
		}else {
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

	/** 
	 * ログイン画面の表示
	 * 
	 */
	@RequestMapping("/loginMenu")
	public String loginMenu() {
		return "/login";
	}

	/**
	 * ログインエラー表示
	 *(メールアドレス、パスワードが違う時)
	 */
	@RequestMapping("/loginError")
	public String login(Model model) {
		model.addAttribute("iserror", true);
		return "forward:/administrator/loginMenu";
	}

	/**
	 * ログアウト機能
	 * 
	 */
	@RequestMapping("/logout")
	public String logout() {
		return "redirect:/administrator/loginMenu";
	}

	/** 
	 * パスワード重複確認
	 * 
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