package com.project.channel.service;

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
    public void inviteToChannel(Map<String,Object>param){
        dao.inviteToChannel(param,session);
    }
}
