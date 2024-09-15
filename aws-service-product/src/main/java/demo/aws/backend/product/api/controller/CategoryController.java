package demo.aws.backend.product.api.controller;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product.service.ProductService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    ProductService productService;

    @GetMapping("/{id}/products")
    public ResponseEntity<Product> getCategory(@PathVariable Long cateId) {
        Optional<Product> optionalProduct = productService.findById(cateId);
        return optionalProduct.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
