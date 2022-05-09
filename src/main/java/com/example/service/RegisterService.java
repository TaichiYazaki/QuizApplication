package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Register;
import com.example.repository.RegisterRepository;

@Service
@Transactional
public class RegisterService {

	@Autowired
	private RegisterRepository registerRepository;

	/*
	 * アカウント登録処理
	 */
	public void insert(Register register) {
		registerRepository.insert(register);
	}
	/*
	 * ログイン処理
	 */
	public Register findAccount(String email, String password) {
		return registerRepository.findAccount(email, password);
	}
	
	/*
	 * メールアドレスが既にDBに登録されているか確認
	 */
	public Register findEmail(String email) {
		return registerRepository.findEmail(email);
	}
}
