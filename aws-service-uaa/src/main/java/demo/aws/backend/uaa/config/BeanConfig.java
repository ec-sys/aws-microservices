package demo.aws.backend.uaa.config;

import demo.aws.core.common_util.JwtService;
import demo.aws.core.framework.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public JwtService jwtService(@Value("${jwt.issuer}") String issuer,
                                 @Value("${jwt.access-token-expire-in-second}") long accessTokenExpireInSecond,
                                 @Value("${jwt.refresh-token-expire-in-second}") long refreshTokenExpireInSecond) {
        return new JwtService(issuer, accessTokenExpireInSecond, refreshTokenExpireInSecond);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
