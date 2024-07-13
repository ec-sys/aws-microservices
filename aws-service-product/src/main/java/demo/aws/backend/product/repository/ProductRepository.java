package demo.aws.backend.product.repository;

import demo.aws.backend.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.id FROM products p ORDER BY p.id ASC LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Long> findAllIdPagination(int limit, int offset);
}
