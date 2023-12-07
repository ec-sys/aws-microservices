package demo.aws.backend.api_gateway.config;

import demo.aws.core.framework.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public JwtService jwtService(@Value("${jwt.issuer}") String issuer,
                                 @Value("${jwt.access-token-expire-in-second}") long accessTokenExpireInSecond,
                                 @Value("${jwt.refresh-token-expire-in-second}") long refreshTokenExpireInSecond) {
        return new JwtService(issuer, accessTokenExpireInSecond, refreshTokenExpireInSecond);
    }
}
