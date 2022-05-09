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
	public void insert(Register register) {
		String insertSql = "INSERT INTO register(name, email, password) VALUES(:name, :email, :password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(register);
		template.update(insertSql, param);
	}

	/*
	 * メールアドレスが既にDBに登録されているか確認
	 */
	public Register findEmail(String email) {
		String findEmailSql = "SELECT * FROM register WHERE email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<Register> registerList = template.query(findEmailSql, param, REGISTER_ROW_MAPPER);
		if (registerList.size() == 0) {
			return null;
		} else {
			return registerList.get(0);
		}
	}

	// アカウントの取得
	public Register findAccount(String email, String password) {
		String findAccountSql = "SELECT * FROM register WHERE email=:email AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("password", password).addValue("email", email);
		List<Register> registerList = template.query(findAccountSql, param, REGISTER_ROW_MAPPER);
		if (registerList.size() == 0) {
			return null;
		} else {
			return registerList.get(0);
		}
	}

}
