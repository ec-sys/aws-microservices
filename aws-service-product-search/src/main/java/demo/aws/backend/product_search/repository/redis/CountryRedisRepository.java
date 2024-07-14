package demo.aws.backend.product_search.repository.redis;

import demo.aws.backend.product_search.domain.entity.redis.CountryRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRedisRepository extends CrudRepository<CountryRedis, Integer> {
}
