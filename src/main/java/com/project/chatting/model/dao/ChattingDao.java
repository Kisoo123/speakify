package com.project.chatting.model.dao;

import com.project.chatting.model.dto.Channel;
import com.project.chatting.model.dto.InnnerChannel;
import com.project.chatting.model.dto.Message;
import com.project.security.model.dto.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChattingDao {
    public Integer checkRoom(SqlSessionTemplate session, Map<String,Object> params) {
        return session.selectOne("Chatting.checkRoom",params);
    }
    public List<Message> checkMessage(SqlSessionTemplate session,int roomNo){
        return session.selectList("Chatting.checkMessage",roomNo);
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
    public List<Channel>getChannelList(SqlSessionTemplate session,int userId) {
        return session.selectList("Chatting.getChannelList",userId);
    }
    public int createChannel(SqlSessionTemplate session, Map<String,Object>params) {
        return session.insert("Chatting.createChannel",params);
    }
    public List<User>getChannelUsers(SqlSessionTemplate session,int roomId) {
        return session.selectList("Chatting.getChannelUsers",roomId);
    }
    public InnnerChannel addInnerChannel(SqlSessionTemplate session, InnnerChannel innerChannel) {
        return session.selectOne("Chatting.addInnerChannel",innerChannel);
    }
}
