package demo.aws.backend.product_cache;

import demo.aws.backend.product_cache.service.ProductDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ProductCacheApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductCacheApplication.class, args);
    }

    @Autowired
    ProductDataService productDataService;

    @Override
    public void run(String... args) throws Exception {
        productDataService.upsertProductToCache();
    }
}
