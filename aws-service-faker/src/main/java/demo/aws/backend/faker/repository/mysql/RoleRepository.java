package demo.aws.backend.faker.repository.mysql;

import demo.aws.backend.uaa.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByName(String roleName);
}

