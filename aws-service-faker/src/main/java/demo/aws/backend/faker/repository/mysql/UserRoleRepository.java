package demo.aws.backend.faker.repository.mysql;

import demo.aws.backend.uaa.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(long userId);

    @Modifying
    @Query("delete from UserRole where userId = ?1")
    void deleteUsersRoles(long userId);
}

