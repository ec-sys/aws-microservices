package demo.aws.backend.near_by.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
public class MessageSubscriber implements MessageListener {

    private SimpMessagingTemplate template;
    private long userId;
    private String loginId;
    public MessageSubscriber(long userId, String loginId, SimpMessagingTemplate template) {
        this.userId = userId;
        this.loginId = loginId;
        this.template = template;
    }

    public long getUserId() {
        return this.userId;
    }
    public void onMessage(Message message, byte[] pattern) {
        String s1 = new String(message.getBody());
        String s2 = new String(message.getChannel());
        String content = "Hello from " + s2 + " with location " + s1;
        template.convertAndSendToUser(loginId, "/queue/messages", content);
        log.info("User {} received message {} by topic {}", userId, s1, s2);
    }
}