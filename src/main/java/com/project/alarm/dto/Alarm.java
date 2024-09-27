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
    private Long id;
    private Long toUser;
    private String type;
    private Timestamp createAt;
    private Timestamp updateAt;
}
