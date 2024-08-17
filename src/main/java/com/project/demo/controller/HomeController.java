package com.project.demo.controller;

import com.project.demo.model.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final DemoService service;
    @RequestMapping("/demo")
    public ResponseEntity<Integer> test(@RequestParam int memberId){
        System.out.println(memberId);
        int result = service.insertName(memberId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }

}
