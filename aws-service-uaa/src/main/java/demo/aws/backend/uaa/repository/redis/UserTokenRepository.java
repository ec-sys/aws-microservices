package demo.aws.backend.uaa.repository.redis;

import demo.aws.backend.uaa.domain.entity.redis.UserToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, String> {
}
