package demo.aws.backend.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class PostApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        showEnvironmentVars();
    }

    private void showEnvironmentVars() {
        log.info("DB_URL: {}", env.getProperty("spring.datasource.url"));
        log.info("DB_USER_NAME: {}", env.getProperty("spring.datasource.username"));
        log.info("DB_PASSWORD: {}", env.getProperty("spring.datasource.password"));
    }
}
