package com.project.chatting.controller;

import com.project.chatting.model.dto.Message;
import com.project.chatting.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        service.sendMessage(message);
        return null;
    }
}
