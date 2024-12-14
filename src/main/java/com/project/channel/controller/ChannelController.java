package com.project.channel.controller;

import com.project.alarm.service.AlarmService;
import com.project.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService service;
    private final AlarmService alarmService;
    @PostMapping("/inviteToChannel")
    public ResponseEntity<?> inviteToChannel(int userId, int friendId,int roomNo){
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("friendId",friendId);
        param.put("roomNo",roomNo);
        service.inviteToChannel(param);
        alarmService.insertAlarm(param);
        return null;
    }
}
