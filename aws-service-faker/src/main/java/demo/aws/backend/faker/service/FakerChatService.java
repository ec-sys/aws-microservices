package demo.aws.backend.faker.service;

import demo.aws.backend.chat.domain.entity.Member;
import demo.aws.backend.faker.repository.mongo.chat.ChatRepository;
import demo.aws.backend.faker.repository.mysql.UserRepository;
import demo.aws.backend.uaa.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakerChatService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;

    public void fakerMembers(int userNumber) {
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
}
