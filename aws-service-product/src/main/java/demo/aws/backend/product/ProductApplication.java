package demo.aws.backend.product;

import demo.aws.backend.product.service.FakerProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProductApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Autowired
    FakerProductService fakerProductService;
    @Override
    public void run(String... args) throws Exception {
        fakerProductService.updateStoreToProduct();
    }
}
