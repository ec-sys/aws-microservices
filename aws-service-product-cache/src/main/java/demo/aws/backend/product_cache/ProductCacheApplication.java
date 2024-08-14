package demo.aws.backend.product_cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProductCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductCacheApplication.class, args);
    }
}
