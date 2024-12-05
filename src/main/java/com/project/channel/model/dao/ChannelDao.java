package com.project.channel.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ChannelDao {
    public void inviteToChannel(Map<String,Object>param, SqlSessionTemplate session){
        session.insert("Channel.inviteToChannel",param);
    }
}
