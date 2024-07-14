package demo.aws.backend.product_search.domain.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("Product")
@Data
public class ProductRedis implements Serializable {
    private Long id;
    private int categoryId;
    private String categoryName;
    private List<Integer> storeIds;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;
}