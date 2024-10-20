package com.project.chatting.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    private int id;
    private Long userId;
    private int roomId;
    private String isChannel;
    private String channelName;
}
