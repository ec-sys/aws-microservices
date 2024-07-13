package demo.aws.backend.product.repository;

import demo.aws.backend.product.domain.entity.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStoreRepository extends JpaRepository<ProductStore, Integer> {
}
