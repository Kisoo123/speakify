package com.project.User.controller;

import com.project.User.service.UserService;
import com.project.aws.s3.service.S3Service;
import com.project.security.model.dto.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.alarm.service.AlarmService.getCurrentUser;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    @Value("${profile.picture.upload.dir}")
    private String uploadDir;

    private final ServletContext servletContext;
    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final S3Service s3Service;

    @PostMapping("/search")
    @ResponseBody
    public List<User> searchUsersByKeyword(@RequestParam("keyword") String keyword) {

        keyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        String displayName = keyword;
        String uniqueTag = null;

        if (keyword.contains("#")) {
            String[] parts = keyword.split("#");
            displayName = parts[0];
            if (parts.length > 1) {
                uniqueTag = parts[1];
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("displayName", displayName);
        params.put("uniqueTag", uniqueTag);
        return service.searchUsersByKeyword(params);
    }

    @PostMapping("/getFriendList")
    @ResponseBody
    public List<User> getFriendList(@RequestParam int userId) {
        return service.getFriendList(userId);
    }


    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture, // 파일 선택적
            @RequestParam(value = "displayName", required = false) String displayName,                // 선택적 문자열
            @RequestParam(value = "statusMessage", required = false) String statusMessage,
            HttpServletRequest request) {
        User user = getCurrentUser();

        if (profilePicture != null) {
            user.setProfilePictureUrl(s3Service.uploadFile(profilePicture));
        }
        user.setDisplayName(displayName);
        user.setStatusMessage(statusMessage);

        // 사용자 정보 업데이트
        service.updateUserProfile(user);
        return "redirect:/";
    }
}
