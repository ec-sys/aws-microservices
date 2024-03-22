package demo.aws.backend.faker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class FakerApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(FakerApplication.class, args);
    }
}
