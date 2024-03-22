package demo.aws.backend.faker.service;

import com.github.javafaker.Faker;
import demo.aws.backend.faker.repository.mysql.RoleRepository;
import demo.aws.backend.faker.repository.mysql.UserRepository;
import demo.aws.backend.faker.repository.mysql.UserRoleRepository;
import demo.aws.backend.uaa.domain.entity.User;
import demo.aws.backend.uaa.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FakerUserService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    public void fakerInsertUsers(int userNumber) {
        Faker faker = new Faker();
        String password = "$2a$10$/kiOmNgtVknPr/uRJAeaYe7nGP6/tGygdzuvANBNdChrEy6I7nFca";// 12345
        // save user
        List<User> userList = new ArrayList<>();
        for(int i = 0;  i < userNumber; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress().toLowerCase());
            user.setLoginId(user.getEmail());
            user.setAddress(faker.address().fullAddress());
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setBirthDate(new Date());
            user.setPassword(password);
            userList.add(user);
        }
        List<User> insertedUsers = userRepository.saveAll(userList);
        // save user role
        List<UserRole> userRoleList = new ArrayList<>();
        insertedUsers.forEach(item -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(item.getId());
            userRole.setRoleId(2);
            userRoleList.add(userRole);
        });
        userRoleRepository.saveAll(userRoleList);
    }
}
