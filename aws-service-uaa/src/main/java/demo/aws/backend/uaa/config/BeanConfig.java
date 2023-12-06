package demo.aws.backend.uaa.config;

import demo.aws.core.framework.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.access-token-expire-in-second}")
    private long accessTokenExpireInSecond;
    @Value("${jwt.refresh-token-expire-in-second}")
    private long refreshTokenExpireInSecond;

    @Bean
    public JwtService jwtService() {
        return new JwtService(issuer, accessTokenExpireInSecond, refreshTokenExpireInSecond);
    }
}
