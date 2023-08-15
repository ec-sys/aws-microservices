package demo.aws.backend.comment.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class LoginInfo {
    private long userId;
    private String firstName;
    private String lastName;
    private String fullName;
    private List<String> roleName;
}