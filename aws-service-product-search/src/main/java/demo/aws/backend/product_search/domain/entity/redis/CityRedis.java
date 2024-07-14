package demo.aws.backend.product_search.domain.entity.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("City")
@Data
public class CityRedis implements Serializable {
    @Id
    private Integer id;
    private String name;
    private int countryId;
}