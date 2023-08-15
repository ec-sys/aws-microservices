package demo.aws.core.framework.security.model;

import lombok.Data;

@Data
public class LoginInfo {
    private long userId;
    private RequestInfo requestInfo;
}
