package demo.aws.backend.product_search;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product_search.domain.entity.elasticsearch.ProductELS;
import demo.aws.backend.product_search.repository.ProductRepository;
import demo.aws.backend.product_search.repository.elasticsearch.ProductELSRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@SpringBootApplication
public class ProductSearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductSearchApplication.class, args);
    }

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductELSRepository productELSRepository;
    @Override
    public void run(String... args) throws Exception {
        Product product = productRepository.findById(1L).get();
        ProductELS productELS = new ProductELS();
        productELS.setName(product.getName());
        productELS.setId(product.getId());
        productELS.setDescription(product.getDescription());
        productELS.setPrice(product.getPrice());
        productELSRepository.save(productELS);

        Page<ProductELS> productByName = productELSRepository.findByName(product.getName(), PageRequest.of(0, 10));
        productByName.getTotalPages();
    }
}
