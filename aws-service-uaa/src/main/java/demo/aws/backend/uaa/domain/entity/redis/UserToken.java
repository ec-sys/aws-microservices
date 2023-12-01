package demo.aws.backend.uaa.domain.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("UserToken")
@Data
public class UserToken implements Serializable {
    private String id;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String privateKey;
    private String publicKey;
    protected Date createdDate;
}
