package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakerDataService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleService userRoleService;

    private void fakeUsers(int userNumber) {
    }
}
