package com.project.webSocket.service;

import com.project.security.model.dto.User;
import com.project.webSocket.dao.AlarmDao;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SqlSessionTemplate session;
    private final AlarmDao dao;

    public String friendRequest(int toUser){
        User user = getCurrentUser();
        HashMap<String, Object> params = new HashMap<>();
        params.put("toUser", toUser);
        params.put("fromUser", user.getId());
        System.out.println(user.getId());
        Integer isRequestPending = dao.findFriendRequest(session,params);
        System.out.println(isRequestPending+"isRequestPending");

        if(isRequestPending!=null){
            if(isRequestPending==((Long)params.get("fromUser")).intValue()){
                System.out.println("myPending");
                return "myPending";
            }else {
                System.out.println("friendPending");
                return "friendPending";
            }
        }
        int result = dao.friendRequest(session,params);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(result>0){
            System.out.println(toUser + "toUser");
            sendFriendRequestNotification(user.getId().intValue(), toUser);
        }
        return "success";
    }

    public void sendFriendRequestNotification(int userId, int friendId) {
        // 친구 신청이 들어온 사용자에게 알림 전송
        messagingTemplate.convertAndSend("/topic/user/" + friendId , userId);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().toString());
        return (User) authentication.getPrincipal();
    }

}
