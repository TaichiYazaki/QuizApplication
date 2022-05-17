package com.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.domain.Quiz;

@SpringBootTest
class QuizRepositoryTest {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private NamedParameterJdbcTemplate template;

	@BeforeEach
	public void testInsert() {
		System.out.println("DB-insert-start");
		Quiz quiz = new Quiz();
		quiz.setQuestion("イギリスの首都は、「ロンドン」である");
		quiz.setAnswer(true);
		quiz.setAuthor("taichi");
		quiz.setAdministratorId(92);
		quizRepository.insert(quiz);
		System.out.println("success-to-insert");
		System.out.println("DB-insert-finish");
	}

	@Test
	public void testLoad() {
		System.out.println("test-load-start");
		Integer maxId = template.queryForObject("SELECT max(id) from quiz;", new MapSqlParameterSource(),
				Integer.class);
		Quiz quiz = quizRepository.load(maxId);
		assertEquals("イギリスの首都は、「ロンドン」である", quiz.getQuestion(), "NotFoundQuestion");
		assertEquals(true, quiz.getAnswer(), "NotFoundAnswer");
		assertEquals("taichi", quiz.getAuthor(), "NotFoundAuthor");

		System.out.println("test-load-finish");
	}

	@Test
	public void list() {
		System.out.println("test-list-start");
		// リスト一覧の取得
		List<Quiz> quizList = quizRepository.list();
		System.out.println(quizList.get(quizList.size() - 1).getQuestion());
		// リストより最初のIDを取得
		Integer minId = template.queryForObject("SELECT min(id) from quiz", new MapSqlParameterSource(), Integer.class);
		Quiz minquiz = quizRepository.load(minId);
		// リストより最後のIDを取得
		Integer maxId = template.queryForObject("SELECT max(id) from quiz;", new MapSqlParameterSource(),
				Integer.class);
		Quiz maxquiz = quizRepository.load(maxId);
		// リストの取り出し
		Integer countId = template.queryForObject("SELECT count(id) FROM quiz;", new MapSqlParameterSource(),
				Integer.class);
		assertEquals(maxquiz.getQuestion(), quizList.get(quizList.size() - 1).getQuestion(), "NotColelctListOrderById");
		assertEquals(minquiz.getQuestion(), quizList.get(0).getQuestion(), "NotCollectListOrderById");
		assertEquals(countId, quizList.size(), "NotCollectSize");
	}

	@AfterEach
	public void tearDownAfterClass() throws Exception {
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("author", "taichi");
		template.update("DELETE FROM quiz WHERE author=:author", param);
		System.out.println("DB-delete");
	}

}
