package demo.aws.backend.chat.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "members")
public class Member extends BasicEntity {
    @Id
    private String id;
    @Indexed
    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthDate;
    private String address;
    private String avatar;
}

