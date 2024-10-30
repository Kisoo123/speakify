package com.project.chatting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    private int id;
    private Long userId;
    private int roomId;
    private String isChannel;
    private String channelName;
    private String channelImage;
}
