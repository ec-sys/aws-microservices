package demo.aws.backend.product_cache.service;

import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductCacheService {
    @Autowired
    CacheManager cacheManager;
    @Autowired
    ProductDataService productDataService;

    public ProductGraphql getByProductId(long productId) {
        Cache cache = cacheManager.getCache("productGraphqls");
        ProductGraphql productGraphql = cache.get(productId, ProductGraphql.class);
        if(Objects.isNull(productGraphql)) {
            productGraphql = productDataService.getProductGraphqls(productId);
            cache.put(productId, productGraphql);
        }
        return productGraphql;
    }

    public List<ProductGraphql> getByProductId(List<Long> productIds) {
        List<ProductGraphql> response = new ArrayList<>();
        Cache cache = cacheManager.getCache("productGraphqls");
        productIds.forEach(id -> {
            ProductGraphql productGraphql = cache.get(id, ProductGraphql.class);
            if(Objects.nonNull(productGraphql)) response.add(productGraphql);
        });
        return response;
    }
}
