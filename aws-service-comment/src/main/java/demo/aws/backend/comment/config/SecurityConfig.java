package demo.aws.backend.comment.config;

import demo.aws.core.framework.security.AbstractSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractSecurityConfig {
}
