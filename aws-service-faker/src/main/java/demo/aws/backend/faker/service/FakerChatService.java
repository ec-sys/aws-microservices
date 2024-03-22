package demo.aws.backend.faker.service;

import demo.aws.backend.chat.domain.entity.Member;
import demo.aws.backend.chat.domain.entity.Room;
import demo.aws.backend.chat.domain.entity.RoomMember;
import demo.aws.backend.chat.domain.enums.MemberStatus;
import demo.aws.backend.chat.domain.enums.RoomType;
import demo.aws.backend.faker.repository.mongo.chat.ChatRepository;
import demo.aws.backend.faker.repository.mysql.UserRepository;
import demo.aws.backend.uaa.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class FakerChatService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;

    public void fakerMembers() {
        List<User> userList = userRepository.findAll();
        List<Member> memberList = new ArrayList<>();
        userList.forEach(item -> {
            Member member = new Member();
            member.setFirstName(item.getFirstName());
            member.setLastName(item.getLastName());
            member.setPhoneNumber(item.getPhoneNumber());
            member.setAddress(item.getAddress());
            memberList.add(member);
        });
        chatRepository.insertAllNoReturn(memberList);
    }

    public void fakerRoomGroupAndMembers() {
        List<Integer> roomSizes = Arrays.asList(5, 50, 100, 200, 500, 1000);
        List<Member> memberList = chatRepository.findAllMember();
        for (Integer size : roomSizes) {// room
            Room room = new Room();
            room.setRoomType(RoomType.GROUP);
            room.setName("ROOM_5");
            room.setDescription("This room hase 5 members");
            room.setDeleted(false);
            room = chatRepository.save(room);

            // room member
            List<RoomMember> roomMembers = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                RoomMember roomMember = new RoomMember();
                roomMember.setRoomId(room.getId());
                roomMember.setMemberStatus(MemberStatus.JOINED);
                roomMember.setMemberId(memberList.get(i).getId());
                roomMembers.add(roomMember);
            }
            chatRepository.insertAllNoReturn(roomMembers);
        }
    }

    public void fakerRoomSingleAndMembers() {
        List<Member> memberList = chatRepository.findAllMember();
        List<RoomMember> roomMembers = new ArrayList<>();
        Random random = new Random();
        for (Member member : memberList) {
            // room
            Member otherMember = memberList.get(random.nextInt(memberList.size() - 1));
            Room room = new Room();
            room.setRoomType(RoomType.INDIVIDUAL);
            room.setName("ROOM_" + otherMember.getLastName());
            room.setDescription("This room hase 1 members");
            room.setDeleted(false);
            room = chatRepository.save(room);

            // room member
            RoomMember roomMember1 = new RoomMember();
            roomMember1.setRoomId(room.getId());
            roomMember1.setMemberStatus(MemberStatus.JOINED);
            roomMember1.setMemberId(member.getId());
            roomMembers.add(roomMember1);

            RoomMember roomMember2 = new RoomMember();
            roomMember2.setRoomId(room.getId());
            roomMember2.setMemberStatus(MemberStatus.JOINED);
            roomMember2.setMemberId(otherMember.getId());
            roomMembers.add(roomMember2);
        }
        chatRepository.insertAllNoReturn(roomMembers);
    }
}
