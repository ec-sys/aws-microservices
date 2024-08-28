package demo.aws.backend.near_by.config;

import demo.aws.core.framework.security.AbstractSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractSecurityConfig {
    @PostConstruct
    public void initialize() {
        this.addWhiteListUrl(Arrays.asList("/ws-chat", "/ws-system", "/ws-group", "/ws-user"));
    }
}
