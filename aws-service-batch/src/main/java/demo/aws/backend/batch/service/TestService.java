package demo.aws.backend.batch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    public void logJobName(String jobName) {
        log.info("starting {} is successful", jobName);
    }
}
