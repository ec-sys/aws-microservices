package demo.aws.backend.chat.domain.entity;

import demo.aws.backend.chat.domain.enums.ConnectedStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Member extends BasicEntity {
    @Id
    private String id;
    @Indexed
    private String userId;
    private ConnectedStatus connectedStatus;
}

