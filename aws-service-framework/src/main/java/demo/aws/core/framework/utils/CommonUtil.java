package demo.aws.core.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.aws.core.framework.constant.URLConstant;
import demo.aws.core.framework.dto.JWTPayloadDto;
import demo.aws.core.framework.security.model.AuthInfo;
import demo.aws.core.framework.security.model.LoginInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommonUtil {
    public static List<String> getPublicUrlPaths() {
        return Arrays.asList(
                URLConstant.LOGIN_URI.replace(URLConstant.PREFIX_UAA_URL, StringUtils.EMPTY),
                URLConstant.REFRESH_TOKEN.replace(URLConstant.PREFIX_UAA_URL, StringUtils.EMPTY)
        );
    }

    public static LoginInfo getLoginInfo() {
        try {
            // get from security context
            LoginInfo loginInfo = (LoginInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return loginInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    public static long getLoginUserId() {
        LoginInfo loginInfo = getLoginInfo();
        if(Objects.isNull(loginInfo)) return 0;
        return loginInfo.getUserId();
    }

    public static String getFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public static String createAuthInfoStr(JWTPayloadDto jwtPayload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AuthInfo authInfo = new AuthInfo();
            authInfo.setUserId(jwtPayload.getUserId());
            authInfo.setLoginId(jwtPayload.getLoginId());
            authInfo.setRoleName(jwtPayload.getRoleNames());
            return mapper.writeValueAsString(authInfo);
        } catch (JsonProcessingException ex) {
            return StringUtils.EMPTY;
        }
    }

    public static String createAuthInfoStr(AuthInfo authInfo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(authInfo);
        } catch (JsonProcessingException ex) {
            return StringUtils.EMPTY;
        }
    }
}
