package demo.aws.backend.rtm.config;

import demo.aws.core.common_util.JwtService;
import demo.aws.core.framework.security.JwtAuthFilter;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public ObservationGrpcClientInterceptor interceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcClientInterceptor(observationRegistry);
    }

    @Bean
    public JwtService jwtService(@Value("${jwt.issuer}") String issuer,
                                 @Value("${jwt.access-token-expire-in-second}") long accessTokenExpireInSecond,
                                 @Value("${jwt.refresh-token-expire-in-second}") long refreshTokenExpireInSecond) {
        return new JwtService(issuer, accessTokenExpireInSecond, refreshTokenExpireInSecond);
    }
}
