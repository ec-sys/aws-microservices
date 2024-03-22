package demo.aws.backend.faker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {
        MongoDataAutoConfiguration.class,
        MongoAutoConfiguration.class
})
public class FakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FakerApplication.class, args);
    }
}
