package demo.aws.core.framework.utils;

import demo.aws.core.framework.constant.URLConstant;
import demo.aws.core.framework.security.model.LoginInfo;
import org.apache.commons.lang3.StringUtils;
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
}
