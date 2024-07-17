package demo.aws.backend.product_search.domain.entity.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("Category")
@Data
public class CategoryRedis implements Serializable {
    @Id
    private Integer id;
    private int parentId;
    private String name;
    private String description;
    private List<Long> productIds;
}
