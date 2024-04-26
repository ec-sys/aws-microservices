package demo.aws.backend.order.config;

import demo.aws.core.framework.security.JwtAuthenticationFilter;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public ObservationGrpcServerInterceptor interceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcServerInterceptor(observationRegistry);
    }
}
