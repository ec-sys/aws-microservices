package demo.aws.backend.post.config;

import demo.aws.core.framework.security.model.AuthInfo;
import demo.aws.core.framework.utils.CommonUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static demo.aws.core.framework.constant.CommonConstant.HEADER_AUTH_INFO;

@Component
public class RestfulRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("non authenticated when call rest template");
        }

        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        template.header(HEADER_AUTH_INFO, CommonUtil.createAuthInfoStr(authInfo));
    }
}