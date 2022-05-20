package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.example.domain.Register;
import com.example.repository.RegisterRepository;

@Service
@Transactional
public class RegisterService {

	@Autowired
	private RegisterRepository registerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	/*
	 * アカウント登録処理
	 * (Spring Security利用中)
	 */
	public Register insert(Register register) {
		register.setPassword(passwordEncoder.encode(register.getPassword()));
		return registerRepository.insert(register);
	}
	/*
	 * ログイン処理
	 */
	public Register findAccount(String email, String password) {
		return registerRepository.findAccount(email, password);
	}
	
	/** 
	 * メールアドレスからユーザー情報の検索処理
	 * (メールアドレス重複確認)
	 */
	public List<Register> findEmail(String email) {
		return registerRepository.findEmail(email);
	}
}
