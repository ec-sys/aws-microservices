package demo.aws.backend.product_search.graphql.resolver;

import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.rest.ProductCacheClient;
import demo.aws.backend.product_search.service.ProductCacheService;
import demo.aws.backend.product_search.service.ProductHazelcastService;
import demo.aws.backend.product_search.service.ProductMySqlService;
import demo.aws.backend.product_search.service.ProductRedisService;
import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    @Autowired
    ProductMySqlService productMySqlService;
    @Autowired
    ProductCacheService productCacheService;
    @Autowired
    ProductRedisService productRedisService;
    @Autowired
    ProductHazelcastService productHazelcastService;

    @Autowired
    ProductCacheClient productCacheClient;

    @QueryMapping
    public ProductGraphql product(@Argument Long id) {
        // return productMySqlService.getProductGraphqlById(id);
        return productCacheClient.findById(id);
    }

    @QueryMapping
    public Iterable<ProductGraphql> productsWithFilter(@Argument ProductFilter filter) {
        switch (filter.getSourceFrom()) {
            case 0:
                return productMySqlService.productsWithFilter(filter);
            case 1:
                return productCacheService.productsWithFilter(filter);
            case 2:
                return productRedisService.productsWithFilter(filter);
            case 3:
                return productHazelcastService.productsWithFilter(filter);
            default:
                throw new IllegalArgumentException("Invalid Source From: " + filter.getSourceFrom());
        }
    }
}
