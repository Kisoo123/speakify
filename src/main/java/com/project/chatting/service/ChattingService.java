package com.project.chatting.service;

import com.project.chatting.model.dao.ChattingDao;
import com.project.chatting.model.dto.Channel;
import com.project.chatting.model.dto.Message;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingDao dao;
    private final SqlSessionTemplate session;

    public Map<String,Object> prepareRoom(Map<String,Object>params){
        Integer roomNo = dao.checkRoom(session,params);
        if(roomNo==null){
            params.put("nextRoomId",dao.getNextRoomId(session));
            params.put("createUser",params.get("myId"));
            int result = dao.createRoom(session,params);
            params.put("createUser",params.get("friendId"));
            result += dao.createRoom(session,params);
        }
        params.put("roomNo",roomNo);
        List<Message> messages = dao.checkMessage(session,params);
        params.put("messages",messages);
        return params;
    }
    public void sendMessage(Message message){
        dao.sendMessage(session, message);
    }
    public List<Channel>getChannelList(String userId){
        return dao.getChannelList(session,userId);
    }
}
