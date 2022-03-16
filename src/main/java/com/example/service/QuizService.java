package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Quiz;
import com.example.repository.QuizRepository;

@Service
@Transactional
public class QuizService {

	@Autowired
	public QuizRepository quizRepository;

	/*
	 * クイズの登録処理
	 */
	public void insert(Quiz quiz) {
		quizRepository.insert(quiz);
	}

	/*
	 * クイズの一覧表示
	 */
	public List<Quiz> list() {
		return quizRepository.list();
	}

	/*
	 * 編集機能 １件の取得
	 */
	public Quiz load(Integer id) {
		return quizRepository.load(id);
	}

	/*
	 * クイズの更新
	 */
	public void update(Quiz quiz) {
		quizRepository.update(quiz);
	}
	
	/*
	 * クイズの削除
	 */
	public void delete(Integer id) {
		quizRepository.delete(id);
	}
}
