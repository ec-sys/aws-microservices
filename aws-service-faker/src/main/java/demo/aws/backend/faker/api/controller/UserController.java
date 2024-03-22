package demo.aws.backend.uaa.api.controller;


import demo.aws.backend.uaa.api.request.UpdateUserRequest;
import demo.aws.backend.uaa.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@AllArgsConstructor
@RequestMapping("user-management/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request) throws Exception {
        try {
            log.info("container ip: {}", InetAddress.getLocalHost().getHostName());
        } catch (Exception ex) {
            log.info("exception : {}", ex.getMessage());
        }
        String errorCode = userService.updateUserBasicInfo(request);
        HttpStatus status = StringUtils.isEmpty(errorCode) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorCode, status);
    }
}
