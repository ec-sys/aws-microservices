package demo.aws.backend.near_by.repository;

import demo.aws.backend.near_by.domain.entity.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
    List<UserFriend> findByUserId(long userId);
}

