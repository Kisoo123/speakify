package com.project.security.model.dao;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.project.security.model.dto.User;
@Repository
@RequiredArgsConstructor
public class SecurityDao {

    private final SqlSessionTemplate session;

    public User findByUsername(String username) {
        return session.selectOne("UserMapper.findByUsername", username);
    }

    public void saveUser(User user) {
        session.insert("UserMapper.saveUser", user);
    }
    public boolean isTagExists(String tag){
        Integer count = session.selectOne("UserMapper.isTagExists", tag);
        return count != null && count > 0;
    }
    public void insertDisplayNumberSequence(){
        session.insert("UserMapper.insertDisplayNumberSequence");
    }
    public int getNextDisplayNumber(){
        return session.selectOne("UserMapper.getNextDisplayNumber");
    }

}
