package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.domain.entity.UserRole;
import demo.aws.backend.uaa.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Transactional
    public void updateUserRole(long userId, List<Long> newRoleIds) {
        userRoleRepository.deleteUsersRoles(userId);

        List<UserRole> newUserRoles = new ArrayList<>();
        newRoleIds.forEach(id -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(id);
            newUserRoles.add(userRole);
        });
        userRoleRepository.saveAll(newUserRoles);
    }

}
