package demo.aws.backend.chat.api.controller;

import demo.aws.backend.chat.api.response.UserRoomsResponse;
import demo.aws.backend.chat.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {

    @Autowired
    RoomService roomService;

    @GetMapping("/{userId}/rooms")
    public ResponseEntity<UserRoomsResponse> getRoomsOfUser(@PathVariable String userId) {
        return new ResponseEntity<>(roomService.getRoomsOfUser(userId), HttpStatus.OK);
    }
}
