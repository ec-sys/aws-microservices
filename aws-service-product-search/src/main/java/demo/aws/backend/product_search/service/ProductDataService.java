package demo.aws.backend.product_search.service;

import demo.aws.backend.product.domain.entity.Category;
import demo.aws.backend.product.domain.entity.City;
import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product_search.domain.entity.elasticsearch.ProductELS;
import demo.aws.backend.product_search.domain.entity.redis.ProductRedis;
import demo.aws.backend.product_search.repository.CategoryRepository;
import demo.aws.backend.product_search.repository.ProductRepository;
import demo.aws.backend.product_search.repository.ProductStoreRepository;
import demo.aws.backend.product_search.repository.elasticsearch.ProductELSRepository;
import demo.aws.backend.product_search.repository.redis.ProductRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductDataService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductELSRepository productELSRepository;
    @Autowired
    ProductRedisRepository productRedisRepository;
    @Autowired
    ProductStoreRepository productStoreRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public void upsertProductFromDBToELS() {
        int totalRecord = (int) productRepository.count();
        int limit = 5000;
        int pageSize = totalRecord / limit;
        if(pageSize * limit < totalRecord) {
            pageSize++;
        }
        log.info("TOTAL PAGE {}, LIMIT {}", pageSize, limit);
        for (int i = 0; i < pageSize; i++) {
            log.info("START GET IDS");
            List<Long> productIds = productRepository.findAllIdPagination(limit, i * limit);
            log.info("DONE GET IDS");
            List<Product> products = productRepository.findAllById(productIds);
            log.info("DONE GET RECORDS");
            List<ProductELS> productELSList = new ArrayList<>();
            for (Product product : products) {
                ProductELS productELS = new ProductELS();
                productELS.setName(product.getName());
                productELS.setId(product.getId());
                productELS.setDescription(product.getDescription());
                productELS.setPrice(product.getPrice());
                productELS.setColor(product.getColor());
                productELS.setMaterial(productELS.getMaterial());
                productELS.setStoreIds(productStoreRepository.findStoreIdOfProduct(product.getId()));
                productELSList.add(productELS);
            }
            productELSRepository.saveAll(productELSList);
            log.info("DONE PAGE {}", i);
        }
    }

    public void upsertProductFromDBToRedis() {
        Map<Integer, Category> categoryMap = categoryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Category::getId, item -> item, (oldValue, newValue) -> newValue));
        int totalRecord = (int) productRepository.count();
        int limit = 5000;
        int pageSize = totalRecord / limit;
        if(pageSize * limit < totalRecord) {
            pageSize++;
        }
        log.info("TOTAL PAGE {}, LIMIT {}", pageSize, limit);
        for (int i = 0; i < pageSize; i++) {
            log.info("START GET IDS");
            List<Long> productIds = productRepository.findAllIdPagination(limit, i * limit);
            log.info("DONE GET IDS");
            List<Product> products = productRepository.findAllById(productIds);
            log.info("DONE GET RECORDS");
            List<ProductRedis> productRedisList = new ArrayList<>();
            for (Product product : products) {
                ProductRedis productRedis = new ProductRedis();
                productRedis.setId(product.getId());
                productRedis.setName(product.getName());
                productRedis.setPrice(product.getPrice());
                productRedis.setColor(product.getColor());
                productRedis.setMaterial(productRedis.getMaterial());
                productRedis.setDescription(product.getDescription());

                Category category = categoryMap.get(product.getCategoryId());
                if(Objects.nonNull(category)) {
                    productRedis.setCategoryId(category.getId());
                    productRedis.setCategoryName(category.getName());
                }
                productRedis.setStoreIds(productStoreRepository.findStoreIdOfProduct(product.getId()));
                productRedisList.add(productRedis);
            }
            productRedisRepository.saveAll(productRedisList);
            log.info("DONE PAGE {}", i);
        }
    }
}
