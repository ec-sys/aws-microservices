package demo.aws.backend.product_search.repository;

import demo.aws.backend.product.domain.entity.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStoreRepository extends JpaRepository<ProductStore, Integer> {
    @Query(value = "SELECT p.storeId FROM ProductStore p WHERE p.productId = ?1")
    List<Integer> findStoreIdOfProduct(long productId);
}
