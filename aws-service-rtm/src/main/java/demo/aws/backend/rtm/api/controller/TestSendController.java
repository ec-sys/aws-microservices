package demo.aws.backend.rtm.api.controller;

import demo.aws.backend.rtm.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("test")
@Slf4j
public class TestSendController {
    private final TestService testService;

    @GetMapping(value = "/send-msg-topic")
    public ResponseEntity<String> sendMessageToTopic(@RequestParam String topic) {
        testService.testSendMessageByUser(topic);
        return new ResponseEntity<>("DONE SEND TO: " + topic, HttpStatus.OK);
    }

    @GetMapping(value = "/send-msg-global")
    public ResponseEntity<String> sendMessageToGlobal() {
        testService.testSendMessageGlobal();
        return new ResponseEntity<>("DONE SEND TO GLOBAL", HttpStatus.OK);
    }
}
