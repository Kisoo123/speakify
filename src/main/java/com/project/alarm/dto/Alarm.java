package com.project.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alarm {
    private int id;
    private Long toUser;
    private String type;
    private Timestamp createAt;
    private Timestamp updateAt;

    // friendRequest 테이블에서 가져올 필드
    private Long friendRequestId;  // friendRequest 테이블의 'id'
    private Long userId;           // friendRequest 테이블의 'user_id'
    private Long friendId;         // friendRequest 테이블의 'friend_id'
    private String status;         // friendRequest 테이블의 'status'
    private Timestamp friendRequestCreatedAt; // friendRequest 테이블의 'created_at'

    // user 테이블에서 가져올 필드
    private Long usrId;                 // 사용자 ID (고유값)
    private String display_name;       // 닉네임 또는 표시 이름

}
