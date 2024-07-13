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
@RequestMapping("/product-crud")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllCategory() {
        return new ResponseEntity<>(CollectionUtils.emptyCollection(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getCategory(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        return optionalProduct.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody Product product) {
        int result = productService.updateProduct(id, product);
        if(result == 1) {
            return new ResponseEntity<Void>( HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>( HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
//        Optional<Category> categoryOptional = categoryService.findById(id);
//        return categoryOptional.map(category -> {
//            categoryService.remove(id);
//            return new ResponseEntity<>(category, HttpStatus.OK);
//        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
}
