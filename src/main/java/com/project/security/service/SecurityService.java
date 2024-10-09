package com.project.security.service;

import com.project.User.controller.TagGenerator;
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
        dao.insertDisplayNumberSequence();
        int displayNumber = dao.getNextDisplayNumber();
        String displayName = "사용자" + displayNumber;
        user.setDisplayName(displayName);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUniqueTag(generateUniqueTag());
        dao.saveUser(user);
    }
    // 고유 태그 생성 메소드
    private String generateUniqueTag() {
        String tag;
        do {
            tag = TagGenerator.generateRandomTag();  // 임의 태그 생성
        } while (dao.isTagExists(tag));  // DB에서 중복 체크
        return tag;
    }
    public User findUserByUsername(String username) {
        return dao.findByUsername(username);
    }
}
