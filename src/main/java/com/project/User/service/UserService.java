package com.project.User.service;

import com.project.User.dao.UserDao;
import com.project.security.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao dao;
    private final SqlSessionTemplate session;

    public List<User> searchUsersByKeyword(Map<String,Object> params){
        return dao.searchUsersByKeyword(params,session);
    }
    public List<User> getFriendList(int userId){
        return dao.getFriendList(session,userId);
    }
}
