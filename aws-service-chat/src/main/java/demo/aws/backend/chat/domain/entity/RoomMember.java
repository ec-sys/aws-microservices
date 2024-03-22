package demo.aws.backend.chat.domain.entity;

import demo.aws.backend.chat.domain.enums.MemberStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room_members")
public class RoomMember extends BasicEntity {
    @Id
    private String id;
    @Indexed
    private String userId;
    @Indexed
    private String roomId;
    private MemberStatus memberStatus;
}