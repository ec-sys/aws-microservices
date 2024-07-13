package demo.aws.backend.product.service;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Optional<Product> findById(long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public int updateProduct(long productId, Product newProduct) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setPrice(newProduct.getPrice());
            productRepository.save(product);
            return 1;
        } else {
            return 0;
        }
    }
}
