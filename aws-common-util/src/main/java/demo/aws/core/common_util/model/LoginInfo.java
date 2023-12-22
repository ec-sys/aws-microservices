package demo.aws.core.common_util.model;

import lombok.Data;

import java.util.List;

@Data
public class LoginInfo {

    private long userId;
    private String loginId;
    private String firstName;
    private String lastName;
    private String fullName;
    private List<String> roleName;
}
