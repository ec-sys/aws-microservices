package demo.aws.backend.product_search.repository;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product_search.graphql.filter.ProductFilter;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Long> findProductIdsByGraphqlFilter(ProductFilter filter);
}
