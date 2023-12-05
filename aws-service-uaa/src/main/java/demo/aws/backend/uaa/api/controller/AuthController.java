package demo.aws.backend.uaa.api.controller;


import demo.aws.backend.uaa.api.request.LoginRequest;
import demo.aws.backend.uaa.api.response.LoginResponse;
import demo.aws.backend.uaa.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        try {
            log.info("container ip: {}", InetAddress.getLocalHost().getHostName());
        } catch (Exception ex) {
            log.info("exception : {}", ex.getMessage());
        }
         LoginResponse response = loginService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
