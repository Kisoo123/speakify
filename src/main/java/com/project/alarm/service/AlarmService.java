package com.project.alarm.service;

import com.project.alarm.dao.AlarmDao;
import com.project.alarm.dto.Alarm;
import com.project.security.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SqlSessionTemplate session;
    private final AlarmDao dao;


    public String friendRequest(int toUser){
        User user = getCurrentUser(); //현재 로그인된 유저의 정보

        HashMap<String, Object> params = new HashMap<>(); //쿼리에 사용할 맵
        params.put("toUser", toUser); //요청을 받는 유저
        params.put("fromUser", user.getId()); //요청을 보내는 유저(현재 유저)

        List<Integer> isRequestPending = dao.findFriendRequest(session,params);
        //이미 요청을 보냈는지 확인, 요청을 보낸 사람의 id를 가져옴

        if(isRequestPending!=null && isRequestPending.size() == 1){ //확인용 변수가 null이 아니고 (요청이 이미 있으면)
            if(isRequestPending.get(0)==((Long)params.get("fromUser")).intValue()){ //보낸 사람이 받는사람이랑 같으면
                return "myPending"; //myPending 스트링을 출력해 프론트단에서 이미 와 있는 요청판정
            }else {
                return "friendPending"; // 아니면(내가 보낸 요청이 이미 있으면) 프론트에서 이미 보낸 요청판정
            }
        }else if(isRequestPending!=null && isRequestPending.size()>1){
            return "alreadyFriend";
        }

        //확인용 변수가 null이면 (요청이 없으면)
        int alarmId = dao.insertFriendRequestAlarm(session,toUser);// 우선 알람을 입력함
        params.put("status", "PENDING");
        if(alarmId!=0){ //알람이 정상적으로 입력되면
            params.put("alarmId",alarmId); //입력한 알람 로우의 id들 맵에 넣음
        }

        int result = dao.friendRequest(session,params); //준비한 정보들을 친구요청 테이블에 입력
        if(result>0){
            sendFriendRequestNotification(user.getId().intValue(), toUser);
            // 정보가 정상적으로 입력되면 알람을 받는 유저에게 실시간 알람을 보냄
        }
        return "success";
    }


    public void sendFriendRequestNotification(int userId, int friendId) {
        // 친구 신청이 들어온 사용자에게 알림 전송
        messagingTemplate.convertAndSend("/topic/user/" + friendId , userId);
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public List<Alarm> findAlarm(int userId){
        return dao.findAlarm(session,userId);
    }

    public String handleFriendRequest(int userId, String action, int alarmId){
        User user = getCurrentUser(); // 현재 로그인된 유저의 정보
        HashMap<String, Object> params = new HashMap<>();
        params.put("toUser", userId);
        params.put("fromUser", user.getId());
        params.put("alarmId", alarmId);

        if(action.equals("accept")){ //만약 친구 요청을 수학했다면
            dao.acceptFriendRequest(session,params); // 보낸 요청의 status를 accept로 update
            //ACCEPTED 상태오 바꿔줄 변수
            params.put("status", "ACCEPTED");
            //friendRequest로 요청을 accpeted 상태로 입력
            dao.friendRequest(session,params);
            // 알람삭제
            dao.deleteAlarm(session,params);
        } else if(action.equals("reject")){// 친구 요청을 거절했다면
            //기존에 보냈던 요청을 삭제함
            dao.rejectFriendRequest(session,params);
            // 알람삭제
            dao.deleteAlarm(session,params);
        }
        return "success";
    }

}
