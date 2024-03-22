package demo.aws.backend.chat.service;

import demo.aws.backend.chat.api.response.RoomMemberResponse;
import demo.aws.backend.chat.domain.entity.RoomMember;
import demo.aws.backend.chat.repository.RoomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomMemberRepository roomMemberRepository;

    public List<RoomMemberResponse> getRoomMembers(String roomId) {
        List<RoomMemberResponse> response = new ArrayList<>();
        List<RoomMember> roomMembers = roomMemberRepository.findByRoomId(roomId);
        roomMembers.forEach(item -> {
            RoomMemberResponse member = new RoomMemberResponse();
            member.setUserId(item.getUserId());
        });
        return response;
    }
}
