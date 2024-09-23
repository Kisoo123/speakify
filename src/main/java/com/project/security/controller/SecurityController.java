package com.project.security.controller;

import com.project.security.model.dto.User;
import com.project.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService service;
    @PostMapping("/register")
    public String saveUser(User user){
        service.saveUser(user);
        return "redirect:/login";
    }
}
