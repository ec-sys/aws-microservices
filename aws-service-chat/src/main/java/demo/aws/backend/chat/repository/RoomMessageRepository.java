package demo.aws.backend.chat.repository;

import demo.aws.backend.chat.domain.entity.RoomMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomMessageRepository extends MongoRepository<RoomMessage, String> {
    List<RoomMessage> findByRoomId(String roomId, Pageable pageable);
}

