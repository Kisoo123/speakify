package com.project.security.service;

import com.project.security.model.dao.SecurityDao;
import com.project.security.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final SecurityDao dao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // User 객체를 Spring Security의 UserDetails로 변환하여 리턴
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())  // 비밀번호는 인코딩된 값이어야 함
                .roles(user.getRole())  // 사용자 권한
                .build();
    }
}
