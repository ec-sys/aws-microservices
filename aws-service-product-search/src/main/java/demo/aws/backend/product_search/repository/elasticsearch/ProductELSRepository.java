package demo.aws.backend.product_search.repository.elasticsearch;

import demo.aws.backend.product_search.domain.entity.elasticsearch.ProductELS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductELSRepository extends ElasticsearchRepository<ProductELS, Long> {
    Page<ProductELS> findByName(String name, Pageable pageable);
}
