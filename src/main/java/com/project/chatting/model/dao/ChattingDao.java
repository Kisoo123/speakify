package com.project.chatting.model.dao;

import com.project.chatting.model.dto.Channel;
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
    public int createRoom(SqlSessionTemplate session, Map<String,Object>params) {
        return session.insert("Chatting.createRoom",params);
    }
    public int getNextRoomId(SqlSessionTemplate session) {
        return session.selectOne("Chatting.getNextRoomId");
    }
    public void sendMessage(SqlSessionTemplate session, Message message) {
        session.insert("Chatting.sendMessage",message);
    }
    public List<Channel>getChannelList(SqlSessionTemplate session,String userId) {
        return session.selectOne("Chatting.getChannelList",userId);
    }
}
