package com.example.repository;

import java.util.List;

import com.example.domain.Favorite;
import com.example.domain.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Favorite> FAVORITE_ROW_MAPPER = (rs, i) -> {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(rs.getInt("favorite_id"));
        favorite.setId(rs.getInt("id"));
        favorite.setAdministratorId(rs.getInt("administrator_id"));
        Quiz quiz = new Quiz();
        quiz.setId(rs.getInt("id"));
        quiz.setQuestion(rs.getString("question"));
        quiz.setAnswer(rs.getBoolean("answer"));
        quiz.setAuthor(rs.getString("author"));
        quiz.setAdministratorId(rs.getInt("administrator_id"));
        favorite.setQuiz(quiz);
        return favorite;
    };

    /**
     * favoriteテーブルに登録
     */
    public void insert(Integer id, Integer administratorId) {
        String sql = "INSERT INTO favorite(id, administrator_id) VALUES (:id, :administratorId)";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("administratorId",
                administratorId);
        template.update(sql, param);
    }

    /**
     * administratorIDからお気に入りしたquizを検索
     */
    public List<Favorite> findByFavorites(Integer administrator_id) {
        String sql = "SELECT f.favorite_id, f.id, q.question, q.answer, q.author, f.administrator_id, r.id "
                + "FROM favorite as f "
                +"JOIN quiz as q "
                + "ON f.id=q.id "
                + "JOIN register as r "
                + "ON f.administrator_id=r.id "
                + "WHERE f.administrator_id=:administrator_id";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("administrator_id", administrator_id);
        List<Favorite> favoriteList = template.query(sql, param, FAVORITE_ROW_MAPPER);
        System.out.println(favoriteList);
        return favoriteList;
    }

    /**
     * お気に入り削除
     */
    public void delete(Integer favoriteId) {
        String deleteSql = "DELETE FROM favorite WHERE favorite_id=:favoriteId";
        SqlParameterSource param = new MapSqlParameterSource().addValue("favoriteId", favoriteId);
        template.update(deleteSql, param);
    }

    /**
     * favorite_idよりadministrator_idを探す
     */
    public Favorite favoriteLoad(Integer favoriteId) {
        String loadSql = "SELECT * FROM favorite WHERE favorite_id=:favoriteId";
        SqlParameterSource param = new MapSqlParameterSource().addValue("favoriteId", favoriteId);
        Favorite favorite = template.queryForObject(loadSql, param, FAVORITE_ROW_MAPPER);
        return favorite;
    }
}
