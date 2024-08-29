package demo.aws.backend.near_by.api;

import demo.aws.backend.near_by.event.HelloMessage;
import demo.aws.core.common_util.model.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public String sendHello(String username) {
        return "Hello, " + username;
    }

    @MessageMapping("/greeting")
    public void sendGreeting(HelloMessage event, Principal principal) {
        Controllers.setAuthentication(principal);
        AuthInfo authInfo = (AuthInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String message = "Hello from " + authInfo.getLoginId();
        simpMessagingTemplate.convertAndSendToUser(event.getToUser(), "/queue/messages", message);
    }
}