package demo.aws.core.framework.config;

import demo.aws.core.framework.security.model.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class ApplicationAuditAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();
        System.out.println("user id - " + authInfo.getUserId());
        return Optional.ofNullable(authInfo.getUserId());
    }
}