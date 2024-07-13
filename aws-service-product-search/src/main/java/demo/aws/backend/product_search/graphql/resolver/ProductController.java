package demo.aws.backend.product_search.graphql.resolver;

import demo.aws.backend.product_search.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    @Autowired
    ProductSearchService productSearchService;
}
