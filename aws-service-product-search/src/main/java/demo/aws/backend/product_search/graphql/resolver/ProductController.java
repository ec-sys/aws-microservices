package demo.aws.backend.product_search.graphql.resolver;

import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.graphql.response.ProductGraphql;
import demo.aws.backend.product_search.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    @Autowired
    ProductSearchService productSearchService;

    @QueryMapping
    public ProductGraphql product(@Argument Long id) {
        return productSearchService.getProductGraphqlById(id);
    }

    @QueryMapping
    public Iterable<ProductGraphql> productsWithFilter(@Argument ProductFilter filter) {
        return productSearchService.productsWithFilter(filter);
    }
}
