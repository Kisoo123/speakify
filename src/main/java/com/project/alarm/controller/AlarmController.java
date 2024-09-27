package com.project.alarm.controller;

import com.project.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService service;

    @GetMapping("/friendRequest")
    public ResponseEntity<?> friendRequest(Long toUser) {
        return ResponseEntity.ok(service.friendRequest(toUser.intValue()));
    }
    @PostMapping("/findAlarm")
    public ResponseEntity<?> findAlarm(@RequestParam int userId) {
        return ResponseEntity.ok(service.findAlarm(userId));
    }
}
