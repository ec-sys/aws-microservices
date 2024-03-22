package demo.aws.backend.chat.repository;

import demo.aws.backend.chat.domain.entity.RoomMember;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomMemberRepository extends MongoRepository<RoomMember, String> {
    List<RoomMember> findByRoomId(String roomId);
}

