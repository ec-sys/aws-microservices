package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.domain.entity.Role;
import demo.aws.backend.uaa.domain.entity.UserRole;
import demo.aws.backend.uaa.repository.RoleRepository;
import demo.aws.backend.uaa.repository.UserRepository;
import demo.aws.backend.uaa.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    public List<String> getRoleOfUser(long userId) {
        List<String> roleNames = new ArrayList<>();
        Set<Long> roleIds = userRoleRepository.findByUserId(userId).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<Role> roles = roleRepository.findAllById(roleIds);
        roles.forEach(item -> {
            roleNames.add(item.getName());
        });
        return roleNames;
    }
}
