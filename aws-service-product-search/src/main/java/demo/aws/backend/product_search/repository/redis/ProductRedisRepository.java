package demo.aws.backend.product_search.repository.redis;

import demo.aws.backend.product_search.domain.entity.redis.ProductRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRedisRepository extends CrudRepository<ProductRedis, Long> {
    List<ProductRedis> findProductRedisByCategoryId(int categoryId);
}
