package demo.aws.backend.near_by.config.ws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * StompBrokerProperties.
     */
    @NonNull
    private final StompBrokerProperties brokerProperties;

    @Autowired
    AuthChannelInterceptor authChannelInterceptor;

    /**
     * {@inheritDoc}.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Use Broker Relay?
        if (brokerProperties.isEnableBrokerRelay()) {
            registry.enableStompBrokerRelay("/topic", "/queue", "/exchange")
                    .setRelayHost(brokerProperties.getRelayHost())
                    .setUserRegistryBroadcast("/exchange/amq.direct/registry.broadcast")
                    .setUserDestinationBroadcast("/exchange/amq.direct/unresolved.user.dest")
                    .setSystemLogin(brokerProperties.getUsername())
                    .setSystemPasscode(brokerProperties.getPassword())
                    .setClientLogin(brokerProperties.getUsername())
                    .setClientPasscode(brokerProperties.getPassword())
                    .setVirtualHost(brokerProperties.getVirtualHost())
                    .setSystemHeartbeatSendInterval(brokerProperties.getHeartbeatSendInterval())
                    .setSystemHeartbeatReceiveInterval(brokerProperties.getHeartbeatReceiveInterval());
        } else {
            // For Unittest
            registry.enableSimpleBroker("/topic", "/queue", "/exchange")
                    .setHeartbeatValue(new long[]{15000, 15000});
        }
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/users");
    }

    /**
     * {@inheritDoc}.
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/cors/CorsConfiguration.html#setAllowedOriginPatterns(java.util.List)
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(getStompEndpoints()).setAllowedOriginPatterns("*");
    }

    private String[] getStompEndpoints() {
        return new String[]{"/ws-location", "/ws-system", "/ws-group", "/ws-user"};
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
                authChannelInterceptor
        );
    }
}