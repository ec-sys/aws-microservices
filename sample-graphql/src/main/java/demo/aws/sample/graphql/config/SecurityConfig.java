package demo.aws.sample.graphql.config;

import demo.aws.core.framework.security.AbstractSecurityConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig extends AbstractSecurityConfig {
    @PostConstruct
    public void initialize() {
        this.addWhiteListUrl(Arrays.asList("/graphiql"));
    }
}
