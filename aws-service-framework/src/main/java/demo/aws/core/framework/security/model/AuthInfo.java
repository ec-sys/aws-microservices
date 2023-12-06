package demo.aws.core.framework.security.model;

import lombok.Data;

import java.util.List;

@Data
public class AuthInfo {
    private long userId;
    private String loginId;
    private List<String> roleName;
}
