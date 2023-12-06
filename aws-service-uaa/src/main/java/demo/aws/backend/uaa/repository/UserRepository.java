package demo.aws.backend.uaa.repository;

import demo.aws.backend.uaa.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long userId);

    User findFirstByLoginId(String loginId);
}

