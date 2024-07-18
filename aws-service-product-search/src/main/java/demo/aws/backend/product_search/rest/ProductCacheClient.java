package demo.aws.backend.product_search.rest;

import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "service-product-cache")
@Service
public interface ProductCacheClient {
    @GetMapping("/product-cache/product-graphqls/{productId}")
    ProductGraphql findById(@PathVariable("productId") Long productId);

    @PostMapping("/product-cache/product-graphqls/find-by-ids")
    List<ProductGraphql> findByIds(@RequestBody List<Long> productIds);
}
