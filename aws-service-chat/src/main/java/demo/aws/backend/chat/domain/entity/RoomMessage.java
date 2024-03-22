package demo.aws.backend.chat.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room_messages")
public class RoomMessage extends BasicMessage {
    @Id
    private String id;
    @Indexed
    private String roomId;
    @Indexed
    private String sentBy;
}

