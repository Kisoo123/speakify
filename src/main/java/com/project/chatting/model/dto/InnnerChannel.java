package com.project.chatting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnnerChannel {
    private int id;
    private String channelName;
    private String channelId;
    private String channelType;
}
