package com.project.chatting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnerChannel {
    private int id;
    private int channelId;
    private String channelName;
    private String channelType;
}
