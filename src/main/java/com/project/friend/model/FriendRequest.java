package com.project.friend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {
    private int id;
    private Long userId;
    private Long friendId;
    private String status;  // "PENDING", "ACCEPTED", "REJECTED"
    private Timestamp createdAt;
    private Long alarmId;

    //친구의 정보
    private Long usrId;                 // 사용자 ID (고유값)
    private String username;          // 사용자명 (로그인 ID)
    private String password;          // 암호화된 비밀번호
    private String role;              // 권한 (예: USER, ADMIN)
    private String profilePictureUrl; // 프로필 사진 URL
    private String email;             // 이메일 주소
    private String display_name;       // 닉네임 또는 표시 이름
    private String statusMessage;     // 상태 메시지 (예: "온라인", "통화중" 등)
    private String uniqueTag; // 유저식별코드
}
