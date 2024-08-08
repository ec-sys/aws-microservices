package demo.aws.backend.product_cache.repository;

import demo.aws.backend.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {
    @Query(value = "SELECT p.id FROM products p", nativeQuery = true)
    List<Long> findAllId();

    @Query(value = "SELECT p.id FROM Product p ORDER BY p.id ASC")
    Page<Integer> findAllIdPagination(Pageable pageable);

    @Query(value = "SELECT p.id FROM products p ORDER BY p.id ASC LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Long> findAllIdPagination(int limit, int offset);

    List<Product> findByCategoryId(int categoryId);

    @Query(value = "SELECT p.id FROM Product p WHERE p.categoryId = ?1")
    List<Long> findProductIdsByCategory(int categoryId);

    @Query(value = "SELECT p.id FROM products p WHERE p.category_id = ?1 AND p.price > ?2 ORDER BY p.price ASC LIMIT ?3 OFFSET ?4", nativeQuery = true)
    List<Long> findProductIdsByFilter(int categoryId, int price, int limit, int offset);
}