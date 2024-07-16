package demo.aws.backend.product_search.graphql.resolver;

import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.graphql.response.ProductGraphql;
import demo.aws.backend.product_search.service.ProductCacheService;
import demo.aws.backend.product_search.service.ProductMySqlService;
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

    @QueryMapping
    public ProductGraphql product(@Argument Long id) {
        return productMySqlService.getProductGraphqlById(id);
    }

    @QueryMapping
    public Iterable<ProductGraphql> productsWithFilter(@Argument ProductFilter filter) {
        if(filter.getIsCache()) {
            return productCacheService.productsWithFilter(filter);
        } else {
            return productMySqlService.productsWithFilter(filter);
        }
    }
}
