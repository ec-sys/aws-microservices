package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.domain.entity.User;
import demo.aws.backend.uaa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakerDataService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private void fakeUsers(int userNumber) {
    }

    public void updatePasswordSalt() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            user.setPassword(passwordEncoder.encode("12345" + user.getPasswordSalt()));
        }
        userRepository.saveAll(userList);
    }
}
