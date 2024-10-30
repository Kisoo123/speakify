package com.project.chatting.service;

import com.project.chatting.model.dao.ChattingDao;
import com.project.chatting.model.dto.Channel;
import com.project.chatting.model.dto.InnnerChannel;
import com.project.chatting.model.dto.Message;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingDao dao;
    private final SqlSessionTemplate session;

    public Map<String,Object> prepareRoom(Map<String,Object>params){
        //본인과 특정친구 1명 총 2명만 있는 방을 고르는 로직
        Integer roomNo = dao.checkRoom(session,params);
        //만약에 없으면 만듦
        if(roomNo==null){
            //다음 방번호 구하기
            params.put("nextRoomId",dao.getNextRoomId(session));
            //해당 방 번호에 본인 추가
            params.put("createUser",params.get("myId"));
            int result = dao.createRoom(session,params);
            //해당 방 번호에 친구 추가
            params.put("createUser",params.get("friendId"));
            result += dao.createRoom(session,params);
        }

        //기존 방에 저장된 채팅 메시지 불러오기
        List<Message> messages = dao.checkMessage(session,roomNo);
        //map에 채팅 저장
        params.put("messages",messages);
        //반환
        return params;
    }
    public void sendMessage(Message message){
        dao.sendMessage(session, message);
    }
    public List<Channel>getChannelList(int userId){
        return dao.getChannelList(session,userId);
    }
    public int createChannel(Map<String,Object> params){
        //새로운 방 번호 받아오기
        int roomNo = dao.getNextRoomId(session);
        //map에 방 번호 저장
        params.put("channelImage",String.valueOf(roomNo)+params.get("channelImage"));
        params.put("roomNo",roomNo);
        //생성된 방 번호를 토대로 방 생성
        dao.createChannel(session,params);
        return roomNo;
    }
    public Map<String,Object> getChannelInfo(int roomId){
        Map<String,Object> channelInfo = new HashMap<>();
        channelInfo.put("channelMessages",dao.checkMessage(session,roomId));
        channelInfo.put("channelUsers",dao.getChannelUsers(session,roomId));
        return channelInfo;
    }
    public InnnerChannel addInnerChannel(InnnerChannel innerChannel){
        return dao.addInnerChannel(session,innerChannel);
    }
}
