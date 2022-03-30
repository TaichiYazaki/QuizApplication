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
	 * クイズの一覧の表示(全件)
	 */
	public List<Quiz> list() {
		return quizRepository.list();
	}
	
	/*
	 * クイズ一覧の表示(自分が投稿したクイズのみ)
	 */
	public List<Quiz> myList(Integer id){
		return quizRepository.myList(id);
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
	
	/*
	 * クイズをランダムで１件取得
	 */
	public List<Quiz> random(){
		return quizRepository.random();
	}
	
	public Boolean check(Integer id, Boolean myAnswer) {
		Boolean check = false;
		Quiz checkQuiz = quizRepository.load(id);
		if(checkQuiz.getAnswer().equals(myAnswer)) {
			check = true;
		}
		return check;
	}
	
}
