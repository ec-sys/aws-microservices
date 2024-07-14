package demo.aws.backend.product_search.repository.redis;

import demo.aws.backend.product_search.domain.entity.redis.StoreRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRedisRepository extends CrudRepository<StoreRedis, Integer> {
}
