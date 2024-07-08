package demo.aws.backend.product.repository;

import demo.aws.backend.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
