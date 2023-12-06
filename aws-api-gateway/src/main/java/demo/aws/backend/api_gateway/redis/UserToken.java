package demo.aws.backend.api_gateway.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("UserToken")
@Data
public class UserToken implements Serializable {
    protected Date createdDate;
    private String id;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String privateKey;
    private String publicKey;
}