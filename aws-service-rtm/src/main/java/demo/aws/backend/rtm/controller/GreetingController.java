package demo.aws.backend.rtm.controller;

import demo.aws.backend.rtm.domain.model.Greeting;
import demo.aws.backend.rtm.domain.model.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Objects;

@Controller
@Slf4j
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(auth)) {
            log.info("Auth is None");
        } else {
            log.info("Auth: {}", auth);
        }
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
