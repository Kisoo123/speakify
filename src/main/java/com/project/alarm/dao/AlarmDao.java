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
    public List<Integer> findFriendRequest(SqlSessionTemplate session, Map<String,Object> param){
        return session.selectList("Alarm.findFriendRequest", param);
    }
    public int insertFriendRequestAlarm(SqlSessionTemplate session, int toUser){
        Alarm alarm = new Alarm();
        alarm.setToUser(Long.valueOf(toUser));
        session.insert("Alarm.insertFriendRequestAlarm", alarm);

       return alarm.getId();
    }
    public List<Alarm> findAlarm(SqlSessionTemplate session, int userId){
        List<Alarm> test = session.selectList("Alarm.findAlarm", userId);
        System.out.println(test.toString());
        return test;
    }
    public void acceptFriendRequest(SqlSessionTemplate session, Map<String, Object> params){
        session.update("Alarm.acceptFriendRequest", params);
    }
    public void rejectFriendRequest(SqlSessionTemplate session, Map<String, Object> params){
        session.delete("Alarm.rejectFriendRequest", params);
    }
    public void deleteAlarm(SqlSessionTemplate session, Map<String, Object> params){
        session.delete("Alarm.deleteAlarm", params);
    }
    public void insertAlarm(SqlSessionTemplate session, Map<String, Object> params){
        session.insert("Alarm.insertAlarm", params);
    }

}
