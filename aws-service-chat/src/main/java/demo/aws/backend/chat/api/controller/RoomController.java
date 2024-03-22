package demo.aws.backend.chat.api.controller;

import demo.aws.backend.chat.api.response.RoomMemberResponse;
import demo.aws.backend.chat.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@Slf4j
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/{roomId}/members")
    public ResponseEntity<List<RoomMemberResponse>> getMembersOfRoom(@PathVariable String roomId) {
        try {
            log.info("container ip: {}", InetAddress.getLocalHost().getHostName());
        } catch (Exception ex) {
            log.info("exception : {}", ex.getMessage());
        }
        return new ResponseEntity<>(roomService.getRoomMembers(roomId), HttpStatus.OK);
    }
}
