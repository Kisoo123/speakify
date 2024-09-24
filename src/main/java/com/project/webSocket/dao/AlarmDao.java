package com.project.webSocket.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AlarmDao {
    public int friendRequest(SqlSessionTemplate session, Map<String,Object> params){
        return session.insert("Alarm.friendRequest", params);
    }
    public Integer findFriendRequest(SqlSessionTemplate session, Map<String,Object> param){
        return session.selectOne("Alarm.findFriendRequest", param);
    }
}
