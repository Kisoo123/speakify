package com.project.webSocket.controller;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class SignalingSocketHandler extends TextWebSocketHandler {

    // 연결된 클라이언트 세션을 저장할 리스트
    private List<WebSocketSession> sessions = new ArrayList<>();

    // 새 연결이 맺어졌을 때 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); // 새로운 세션을 리스트에 추가
        System.out.println("New session added: " + session.getId());
    }

    // 클라이언트가 메시지를 보냈을 때 호출
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Message received from session " + session.getId() + ": " + message.getPayload());

        // 받은 메시지를 다른 모든 세션에 전달 (브로드캐스팅)
        for (WebSocketSession s : sessions) {
            if (s.isOpen() && !s.getId().equals(session.getId())) {
                s.sendMessage(message); // 메시지를 다른 클라이언트에게 전달
            }
        }
    }

    // 연결이 종료되었을 때 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session); // 세션을 리스트에서 제거
        System.out.println("Session removed: " + session.getId());
    }
}
