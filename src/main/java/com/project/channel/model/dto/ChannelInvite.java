package com.project.channel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelInvite {
    private int id;
    private String status;
    private Timestamp createdAt;
    private int channelId;
    private int alarmId;
    private int toUser;
    private int fromUser;
}
