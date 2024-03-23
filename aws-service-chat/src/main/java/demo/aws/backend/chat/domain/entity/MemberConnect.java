package demo.aws.backend.chat.domain.entity;

import demo.aws.backend.chat.domain.enums.ConnectedStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "member_connects")
public class MemberConnect extends BasicEntity {
    @Id
    private String id;
    @Indexed
    private String memberId;
    private ConnectedStatus connectedStatus;
}

