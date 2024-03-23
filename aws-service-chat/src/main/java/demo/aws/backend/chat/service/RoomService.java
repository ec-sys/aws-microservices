package demo.aws.backend.chat.service;

import demo.aws.backend.chat.api.response.RoomGroupResponse;
import demo.aws.backend.chat.api.response.RoomMemberResponse;
import demo.aws.backend.chat.api.response.RoomSingleResponse;
import demo.aws.backend.chat.api.response.UserRoomsResponse;
import demo.aws.backend.chat.domain.entity.Member;
import demo.aws.backend.chat.domain.entity.Room;
import demo.aws.backend.chat.domain.entity.RoomMember;
import demo.aws.backend.chat.domain.enums.RoomType;
import demo.aws.backend.chat.repository.MemberRepository;
import demo.aws.backend.chat.repository.RoomMemberRepository;
import demo.aws.backend.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomMemberRepository roomMemberRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoomRepository roomRepository;

    public List<RoomMemberResponse> getRoomMembers(String roomId) {
        List<RoomMemberResponse> response = new ArrayList<>();
        List<RoomMember> roomMembers = roomMemberRepository.findByRoomId(roomId);
        roomMembers.forEach(item -> {
            RoomMemberResponse member = new RoomMemberResponse();
            member.setMemberId(item.getMemberId());
        });
        return response;
    }

    public UserRoomsResponse getRoomsOfUser(String userId) {
        UserRoomsResponse response = new UserRoomsResponse();
        // member from user
        Member member = memberRepository.findFirstByUserId(userId);
        // rooms of member
        List<RoomMember> roomMembers = roomMemberRepository.findByMemberId(member.getId());
        List<String> roomIds = roomMembers.stream().map(RoomMember::getRoomId).collect(Collectors.toList());
        List<Room> rooms = roomRepository.findByIdIn(roomIds);
        // single/group rooms
        List<RoomGroupResponse> groupRooms = new ArrayList<>();
        List<RoomSingleResponse> singleRooms = new ArrayList<>();
        rooms.forEach(item -> {
            if(RoomType.GROUP.equals(item.getRoomType())) {
                groupRooms.add(buildRoomGroup(item));
            } else {
                singleRooms.add(buildRoomSingle(item));
            }
        });
        response.setGroupRooms(groupRooms);
        response.setSingleRooms(singleRooms);
        return response;
    }

    private RoomGroupResponse buildRoomGroup(Room item) {
        RoomGroupResponse room = new RoomGroupResponse();
        room.setRoomId(item.getId());
        room.setRoomName(item.getName());
        room.setAvatar(room.getAvatar());
        return room;
    }

    private RoomSingleResponse buildRoomSingle(Room item) {
        RoomSingleResponse room = new RoomSingleResponse();
        room.setRoomId(item.getId());
        room.setRoomName(item.getName());
        room.setAvatar(room.getAvatar());
        return room;
    }
}
