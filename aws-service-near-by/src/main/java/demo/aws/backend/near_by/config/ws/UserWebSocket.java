package demo.aws.backend.near_by.config.ws;

import demo.aws.core.common_util.model.AuthInfo;

import java.security.Principal;

public class UserWebSocket implements Principal {
    private AuthInfo authInfo;

    public UserWebSocket(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public String getName() {
        return authInfo.getLoginId();
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }
}
