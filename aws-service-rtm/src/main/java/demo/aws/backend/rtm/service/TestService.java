package demo.aws.backend.rtm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class TestService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    public void testSendMessageByUser(String topic) {
        messagingTemplate.convertAndSend(topic, "hello you, this is test message");
    }

    public void testSendMessageGlobal() {
        Random random = new Random();
        int number = random.nextInt();
        messagingTemplate.convertAndSend("/topic/global", "hello you, this is test message: " + number);
    }
}
