package demo.aws.backend.chat.domain.entity;

import demo.aws.backend.chat.domain.enums.RoomType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "rooms")
public class Room extends BasicEntity {
    @Id
    private String id;
    private String name;
    private String avatar;
    private String description;
    private RoomType roomType;
    private boolean deleted;
    private Date lastActionDate;
}
