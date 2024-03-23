package demo.aws.backend.rtm.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_topics")
public class UserTopic extends BasicEntity {
    @Id
    private String id;
    @Indexed
    private String userId;
    @Indexed
    private String topicId;
}

