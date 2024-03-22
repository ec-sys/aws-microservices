package demo.aws.backend.faker.api.controller;

import demo.aws.backend.faker.service.FakerChatService;
import demo.aws.backend.faker.service.FakerUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/chats")
@Slf4j
public class FakerChatController {
    @Autowired
    FakerChatService fakerChatService;

    @GetMapping(value = "/fake-members")
    public ResponseEntity<String> fakeUsers() throws Exception {
        fakerChatService.fakerMembers();
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }

    @GetMapping(value = "/fake-room-group")
    public ResponseEntity<String> fakeRoomGroup() throws Exception {
        fakerChatService.fakerRoomGroupAndMembers();
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }

    @GetMapping(value = "/fake-room-single")
    public ResponseEntity<String> fakeRoomSingle() throws Exception {
        fakerChatService.fakerRoomSingleAndMembers();
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }
}
