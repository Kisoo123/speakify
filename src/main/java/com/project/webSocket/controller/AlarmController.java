package com.project.webSocket.controller;

import com.project.webSocket.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService service;

    @GetMapping("/friendRequest")
    public ResponseEntity<?> friendRequest(Long toUser) {
        return ResponseEntity.ok(service.friendRequest(toUser.intValue()));
    }
}
