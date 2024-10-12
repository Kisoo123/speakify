package com.project.User.controller;

import com.project.User.service.UserService;
import com.project.security.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService service;
    @PostMapping("/search")
    @ResponseBody
    public List<User> searchUsersByKeyword(@RequestParam("keyword") String keyword) {

        System.out.println(keyword +"controller Keyword");
        keyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        System.out.println(keyword + " decoded 키워드");
        String displayName = keyword;
        String uniqueTag = null;

        if (keyword.contains("#")) {
            String[] parts = keyword.split("#");
            displayName = parts[0];
            if (parts.length > 1) {
                uniqueTag = parts[1];
            }
        }
        Map<String,Object> params = new HashMap<>();
        params.put("displayName", displayName);
        params.put("uniqueTag", uniqueTag);
        return service.searchUsersByKeyword(params);
    }
    @PostMapping("/getFriendList")
    @ResponseBody
    public List<User> getFriendList(@RequestParam int userId){
        return service.getFriendList(userId);
    }
}
