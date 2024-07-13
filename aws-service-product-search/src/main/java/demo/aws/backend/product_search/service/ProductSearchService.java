package demo.aws.backend.product_search.service;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product_search.graphql.response.ProductGraphql;
import demo.aws.backend.product_search.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductSearchService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductStoreRepository productStoreRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CountryRepository countryRepository;
    @QueryMapping
    public Optional<ProductGraphql> product(long id) {
        ProductGraphql response = new ProductGraphql();
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()) {
            return Optional.empty();
        } else {
            Product product = productOptional.get();
            response.setId(product.getId());
            response.setCategoryId(product.getCategoryId());
            response.setName(product.getName());
            response.setImage(product.getImage());
            response.setColor(product.getColor());
            response.setMaterial(product.getMaterial());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
        }
        return null;
    }
}
