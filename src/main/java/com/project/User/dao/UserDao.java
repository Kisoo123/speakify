package com.project.User.dao;

import com.project.security.model.dto.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    public List<User> searchUsersByKeyword(Map<String,Object> params, SqlSessionTemplate session){
        System.out.println(params.get("displayName")+"dao 파라미터 keyword");

        return session.selectList("searchUsersByKeyword",params);
    }
    public List<User> getFriendList(SqlSessionTemplate session,int userId){
        return session.selectList("getFriendList",userId);
    }
}
