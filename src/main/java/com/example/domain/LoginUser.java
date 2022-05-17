package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * 管理者のログイン情報を格納するエンティティ
 * 
 */
public class LoginUser extends User {

    private static final long serialVersionUID = 1L;

    private final Register register;

    public LoginUser(Register register, Collection<GrantedAuthority> authorityList) {
        super(register.getEmail(), register.getPassword(), authorityList);
        this.register = register;
    }

    public Register getRegister(){
        return register;
    }
    
}
