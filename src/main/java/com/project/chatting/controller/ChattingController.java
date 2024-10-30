package com.project.chatting.controller;

import com.project.aws.s3.service.S3Service;
import com.project.chatting.model.dto.Channel;
import com.project.chatting.model.dto.InnnerChannel;
import com.project.chatting.model.dto.Message;
import com.project.chatting.model.dto.SignalMessage;
import com.project.chatting.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.alarm.service.AlarmService.getCurrentUser;

@RequiredArgsConstructor
@Controller
public class ChattingController {
    private final S3Service s3Service;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChattingService service;
    @PostMapping("/startCall")
    @ResponseBody
    public ResponseEntity<?> startCall(@RequestParam int myId,@RequestParam int friendId) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("myId", myId);
        params.put("friendId", friendId);
        Map<String,Object> result =  service.prepareRoom(params);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        message.setMessage(StringEscapeUtils.escapeHtml4(message.getMessage()));
        service.sendMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/room/"+message.getChannelId(),message);
        return null;
    }
    // 클라이언트가 전송한 메시지를 수신
    @MessageMapping("/signal/{roomNo}")
    @SendTo("/topic/signal/{roomNo}")
    public SignalMessage handleSignal(@Payload SignalMessage signalMessage, @DestinationVariable String roomNo) {
        // 여기서 signalMessage는 클라이언트가 전송한 offer, answer, ICE 후보 등의 정보
        return signalMessage; // 방에 있는 다른 클라이언트들에게 브로드캐스트
    }
    @PostMapping("/getChannelList")
    @ResponseBody
    public List<Channel> getChannelList(@RequestParam int userId) {
        return service.getChannelList(userId);
    }
    @PostMapping("/createChannel")
    public String createChannel(
            @RequestParam MultipartFile channelImage,
            @RequestParam String channelName)
    {
        int userId = getCurrentUser().getId().intValue();
        String path = "uploads/public/channel/";
        String imageName = channelImage.isEmpty()?"SPEAKIFY_1.png":channelImage.getOriginalFilename();

        Map<String,Object> params = new HashMap<>();
        params.put("channelName", channelName);
        params.put("userId", userId);
        params.put("channelImage", imageName);
        int resultChannel = service.createChannel(params);
        if ( resultChannel >0 ) {
            if(!channelImage.isEmpty()) {
                s3Service.uploadFile(channelImage, path,resultChannel);
            }
        }
        return "redirect:/";
    }
    @PostMapping("/getChannelInfo")
    public ResponseEntity<?> getChannelInfo(@RequestParam int roomId) {
        return ResponseEntity.ok(service.getChannelInfo(roomId));
    }
    @PostMapping("/addInnerChannel")
    public ResponseEntity<?>addInnerChannel(@RequestParam int channelId,
                                            @RequestParam String channelType,
                                            @RequestParam String channelName){
        InnnerChannel innerChannel = new InnnerChannel();
        innerChannel.setId(channelId);
        innerChannel.setChannelType(channelType);
        innerChannel.setChannelName(channelName);
        return ResponseEntity.ok(service.addInnerChannel(innerChannel));
    }
}
