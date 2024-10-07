package com.project.chatting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignalMessage {

    private String type;      // offer, answer, candidate
    private String sdp;       // offer나 answer일 때의 SDP 정보
    private String candidate; // ICE candidate 정보
    private String roomNo;    // 방 번호, 어떤 방에서 발생한 신호인지 구분하기 위함
}
