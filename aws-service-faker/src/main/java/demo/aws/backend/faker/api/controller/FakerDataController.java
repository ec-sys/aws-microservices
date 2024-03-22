package demo.aws.backend.uaa.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@AllArgsConstructor
@RequestMapping("faker-data")
@Slf4j
public class FakerDataController {
    @GetMapping(value = "/users")
    public ResponseEntity<String> getPublicKey() throws Exception {
        try {
            log.info("container ip: {}", InetAddress.getLocalHost().getHostName());
        } catch (Exception ex) {
            log.info("exception : {}", ex.getMessage());
        }
        return new ResponseEntity<>("DONE-" + InetAddress.getLocalHost().getHostName(), HttpStatus.OK);
    }
}
