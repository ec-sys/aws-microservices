package demo.aws.backend.chat.repository;

import demo.aws.backend.chat.domain.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByIdIn(List<String> roomIds);
}

