package demo.aws.backend.rtm.config.ws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * StompBrokerProperties.
 */
@ConfigurationProperties(prefix = "stomp.broker")
@Data
@Component
public class StompBrokerProperties {

    /**
     * Enable broker relay.
     */
    private boolean enableBrokerRelay = false;

    /**
     * Relay hostname.
     */
    private String relayHost = "localhost";

    /**
     * Relay port.
     */
    private int relayPort = 61613;

    /**
     * Broker username.
     */
    private String username = "guest" ;

    /**
     * Broker password.
     */
    private String password = "guest";

    /**
     * Virtual host.
     */
    private String virtualHost = "/";

    /**
     * heartbeatSendInterval.
     */
    private long heartbeatSendInterval = 4000;

    /**
     * heartbeatReceiveInterval.
     */
    private long heartbeatReceiveInterval = 4000;
}