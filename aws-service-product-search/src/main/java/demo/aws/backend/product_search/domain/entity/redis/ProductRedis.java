package demo.aws.backend.product_search.domain.entity.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@RedisHash("Product")
@Data
public class ProductRedis implements Serializable {
    @Id
    private Long id;
    @Indexed
    private int categoryId;
    private List<Integer> storeIds;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;
}