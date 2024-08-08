package demo.aws.backend.product_cache.api.rest.controller;

import demo.aws.backend.product_cache.service.ProductCacheService;
import demo.aws.core.common_util.graphql.product.ProductGraphql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-cache/product-graphqls")
@Slf4j
public class ProductCacheController {
    @Autowired
    ProductCacheService cacheService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductGraphql> findById(@PathVariable long productId) {
        return new ResponseEntity<>(cacheService.getByProductId(productId), HttpStatus.OK);
    }

    @PostMapping("/find-by-ids")
    public ResponseEntity<List<ProductGraphql>> findById(@RequestBody List<Long> productIds) {
        return new ResponseEntity<>(cacheService.getByProductId(productIds), HttpStatus.OK);
    }
}
