package demo.aws.backend.product_search.repository.redis;

import demo.aws.backend.product_search.domain.entity.redis.CityRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRedisRepository extends CrudRepository<CityRedis, Integer> {
}
