package com.project.demo.controller;

import com.project.demo.model.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final DemoService service;
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }
    @GetMapping("/register")
    public String register() {
        return "security/register";
    }

    @GetMapping("/aws")
    public ResponseEntity<String> aws() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
