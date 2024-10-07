package com.project.chatting.controller;

import com.project.chatting.model.dto.Message;
import com.project.chatting.model.dto.SignalMessage;
import com.project.chatting.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ChattingController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChattingService service;
    @PostMapping("/startCall")
    @ResponseBody
    public ResponseEntity<?> startCall(@RequestParam int myId,@RequestParam int friendId) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("myId", myId);
        params.put("friendId", friendId);
        Map<String,Object> result =  service.prepareRoom(params);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        System.out.println(message.toString()+"메시지 테스트");
        service.sendMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/room/"+message.getChannelId(),message);
        return null;
    }
    // 클라이언트가 전송한 메시지를 수신
    @MessageMapping("/signal/{roomNo}")
    @SendTo("/topic/signal/{roomNo}")
    public SignalMessage handleSignal(@Payload SignalMessage signalMessage, @DestinationVariable String roomNo) {
        // 여기서 signalMessage는 클라이언트가 전송한 offer, answer, ICE 후보 등의 정보
        return signalMessage; // 방에 있는 다른 클라이언트들에게 브로드캐스트
    }
}
