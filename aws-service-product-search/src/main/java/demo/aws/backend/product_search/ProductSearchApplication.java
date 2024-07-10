package demo.aws.backend.product_search;

import demo.aws.backend.product_search.service.ProductDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProductSearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductSearchApplication.class, args);
    }

    @Autowired
    ProductDataService productDataService;
    @Override
    public void run(String... args) throws Exception {
        productDataService.upsertProductFromDBToRedis();
    }
}
