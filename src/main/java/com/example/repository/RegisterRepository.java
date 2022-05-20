package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Register;

@Repository
public class RegisterRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Register> REGISTER_ROW_MAPPER = (rs, i) -> {
		Register register = new Register();
		register.setId(rs.getInt("id"));
		register.setName(rs.getString("name"));
		register.setEmail(rs.getString("email"));
		register.setPassword(rs.getString("password"));
		return register;
	};

	/*
	 * アカウントの登録
	 */
	public Register insert(Register register) {
		String insertSql = "INSERT INTO register(name, email, password) VALUES(:name, :email, :password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(register);
		template.update(insertSql, param);
		return register;
	}

	/** 
	 * メールアドレスからユーザー情報の検索処理
	 * 
	 */
	public List<Register> findEmail(String email) {
		String findEmailSql = "SELECT * FROM register WHERE email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<Register> registerList = template.query(findEmailSql, param, REGISTER_ROW_MAPPER);
		return registerList;
	}

	// アカウントの取得
	public Register findByMailAddress(String email) {
		String findAccountSql = "SELECT * FROM register WHERE email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<Register> registerList = template.query(findAccountSql, param, REGISTER_ROW_MAPPER);
		if (registerList.size() == 0) {
			return null;
		}
		return registerList.get(0);

	}

	public Register findAccount(String email, String password) {
		String findAccountSql = "SELECT * FROM register WHERE email=:email AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<Register> registerList = template.query(findAccountSql, param, REGISTER_ROW_MAPPER);
		if (registerList.size() == 0) {
			return null;
		}
		return registerList.get(0);

	}
}
