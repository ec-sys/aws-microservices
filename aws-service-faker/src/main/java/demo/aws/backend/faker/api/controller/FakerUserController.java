package demo.aws.backend.faker.api.controller;

import demo.aws.backend.faker.service.FakerUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Slf4j
public class FakerUserController {
    @Autowired
    FakerUserService fakerUserService;

    @GetMapping(value = "/create-users/{userNumber}")
    public ResponseEntity<String> fakeUsers(@PathVariable int userNumber) throws Exception {
        fakerUserService.fakerInsertUsers(userNumber);
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }
}
