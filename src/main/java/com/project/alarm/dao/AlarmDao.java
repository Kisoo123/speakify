package com.project.alarm.dao;

import com.project.alarm.dto.Alarm;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AlarmDao {
    public int friendRequest(SqlSessionTemplate session, Map<String,Object> params){
        return session.insert("Alarm.friendRequest", params);
    }
    public Integer findFriendRequest(SqlSessionTemplate session, Map<String,Object> param){
        return session.selectOne("Alarm.findFriendRequest", param);
    }
    public int insertFriendRequestAlarm(SqlSessionTemplate session, int toUser){
        System.out.println(toUser + "테스트용 dao");
        Alarm alarm = new Alarm();
        alarm.setToUser(Long.valueOf(toUser));
        session.insert("Alarm.insertFriendRequestAlarm", alarm);

       return alarm.getId().intValue();
    }
    public List<Alarm> findAlarm(SqlSessionTemplate session, int userId){
        return session.selectList("Alarm.findAlarm", userId);
    }
}
