package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.api.request.UpdateUserRequest;
import demo.aws.backend.uaa.domain.entity.User;
import demo.aws.backend.uaa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String updateUserBasicInfo(UpdateUserRequest request) {
        User user = userRepository.findById(request.getUserId());
        if (Objects.isNull(user)) {
            log.error("user id {} isn't not exist", request.getUserId());
            return "ERROR_USER_0001";
        }
        if(Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if(Objects.nonNull(request.getPhoneNumber())) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        userRepository.save(user);
        return StringUtils.EMPTY;
    }
}
