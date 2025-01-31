package com.project.channel.service;

import com.project.alarm.dao.AlarmDao;
import com.project.channel.model.dao.ChannelDao;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final SqlSessionTemplate session;
    private final ChannelDao dao;
    private final AlarmDao alarmDao;
    public void inviteToChannel(Map<String,Object>param){
        //같은 요청이 없으면
        if(dao.selectInviteRequest(param,session)){
            int alarmId = alarmDao.insertAlarm(session,param);
            param.put("alarmId",alarmId);
            dao.inviteToChannel(param,session);
        }else {
            System.out.println("요청있음");
        }
    }
}
