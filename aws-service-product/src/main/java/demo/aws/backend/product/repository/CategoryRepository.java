package demo.aws.backend.product.repository;

import demo.aws.backend.product.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
