package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Quiz;

@Repository
public class QuizRepository {

	/*
	 * データベースに繋ぐ処理
	 */
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Quiz> QUIZ_ROW_MAPPER = (rs, i) -> {
		Quiz quiz = new Quiz();
		quiz.setId(rs.getInt("id"));
		quiz.setQuestion(rs.getString("question"));
		quiz.setAnswer(rs.getBoolean("answer"));
		quiz.setAuthor(rs.getString("author"));
		quiz.setAdministratorId(rs.getInt("administrator_id"));
		return quiz;
	};

	/*
	 * クイズの登録処理
	 */
	public void insert(Quiz quiz) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(quiz);
		String insertSql = "INSERT INTO quiz(question, answer, author, administrator_id) VALUES(:question, :answer, :author,:administratorId)";
		template.update(insertSql, param);
	}

	/*
	 * クイズ一覧の表示() 登録日時を作りたい
	 */
	public List<Quiz> list() {
		String listSql = "SELECT * FROM quiz ORDER BY id";
		List<Quiz> list = template.query(listSql, QUIZ_ROW_MAPPER);
		return list;
	}
	
	/*
	 * クイズ一覧(自分が投稿したクイズのみを表示)
	 * 
	*/
	
	public List<Quiz> myList(Integer id){
		String myListSql = "SELECT q.id,q.question,q.answer,q.author,q.administrator_id,"
				           + " r.id"
				           + " FROM quiz as q"
				           + " INNER JOIN register as r"
				           + " ON q.administrator_id=r.id"
				          //+" WHERE q.administrator_id=:administratorId";
				           + " WHERE r.id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		List<Quiz> myList = template.query(myListSql, param, QUIZ_ROW_MAPPER);
		return myList;
	} 

	/*
	 * 編集機能 １件を取得してくる
	 */

	public Quiz load(Integer id) {
		String loadSql = "SELECT * FROM quiz WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Quiz quiz = template.queryForObject(loadSql, param, QUIZ_ROW_MAPPER);
		return quiz;
	}

	/*
	 * 更新処理
	 */
	public void update(Quiz quiz) {
		String updateSql = "UPDATE quiz SET question=:question, answer=:answer, author=:author WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(quiz);
		template.update(updateSql, param);
	}

	/*
	 * 削除機能
	 */
	public void delete(Integer id) {
		String deleteSql = "DELETE FROM quiz WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(deleteSql, param);
	}

	/*
	 * ランダムでクイズを取得
	 */
	public List<Quiz> random() {
		String randSql = "SELECT * FROM quiz ORDER BY RANDOM() LIMIT 1";
		List<Quiz> quiz = template.query(randSql, QUIZ_ROW_MAPPER);
		return quiz;
	}

	/**
	 * 
	 */
}
