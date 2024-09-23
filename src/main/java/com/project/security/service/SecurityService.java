package com.project.security.service;

import com.project.security.model.dao.SecurityDao;
import com.project.security.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final SqlSessionTemplate session;
    private final PasswordEncoder passwordEncoder;
    private final SecurityDao dao;

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.saveUser(user);
    }
}
