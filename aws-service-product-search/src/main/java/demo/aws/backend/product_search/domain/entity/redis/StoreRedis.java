package demo.aws.backend.product_search.domain.entity.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Store")
@Data
public class StoreRedis implements Serializable {
    @Id
    private Integer id;
    private String address;
    private String address2;
    private int cityId;
    private String district;
    private String postalCode;
    private String phone;
}