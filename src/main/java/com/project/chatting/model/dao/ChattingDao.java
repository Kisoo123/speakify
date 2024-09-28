package com.project.chatting.model.dao;

import com.project.chatting.model.dto.Message;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChattingDao {
    public Integer checkRoom(SqlSessionTemplate session, Map<String,Object> params) {
        return session.selectOne("Chatting.checkRoom",params);
    }
    public List<Message> checkMessage(SqlSessionTemplate session,Map<String,Object>params){
        return session.selectList("Chatting.checkMessage",params);
    }
    public int createRoom(SqlSessionTemplate session, int userId) {
        return session.insert("Chatting.createRoom",userId);
    }
}
