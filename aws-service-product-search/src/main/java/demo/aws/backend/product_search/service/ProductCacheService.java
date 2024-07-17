package demo.aws.backend.product_search.service;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product_search.graphql.filter.FilterField;
import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.graphql.response.CategoryGraphql;
import demo.aws.backend.product_search.graphql.response.ProductGraphql;
import demo.aws.backend.product_search.graphql.response.StoreGraphql;
import demo.aws.backend.product_search.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductCacheService {
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

    @Autowired
    ProductSearchCommon searchCommon;

    @Autowired
    ProductRedisService productRedis;

    @Autowired
    CacheManager cacheManager;

    public List<ProductGraphql> productsWithFilter(ProductFilter filter) {
        List<ProductGraphql> response = new ArrayList<>();
        int categoryId = filter.getCategoryId();
        Cache cache = cacheManager.getCache("productGraphqlsByCategory");
        String cacheKey = "cate_" + categoryId;
        List<ProductGraphql> productGraphqls = new ArrayList<>();
        if(Objects.isNull(cache.get(cacheKey))) {
            productGraphqls = getProductGraphqlsByCategory(filter.getCategoryId());
            cache.put(cacheKey, productGraphqls);
        } else {
            productGraphqls = (List<ProductGraphql>) cache.get(cacheKey).get();
        }
        int limit = filter.getPageSize();
        int offset = filter.getPageNumber() * limit;
        response = productGraphqls.stream().filter(buildFilter(filter))
                .sorted(Comparator.comparing(o -> o.getPrice()))
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        return response;
    }

    private Predicate<ProductGraphql> buildFilter(ProductFilter filter) {
        Predicate<ProductGraphql> spec = byCategory(filter.getCategoryId());
        if (Objects.nonNull(filter.getPrice())) {
            spec = spec.and(byPrice(filter.getPrice()));
        }
        return spec;
    }

    // @Cacheable(value = "productGraphqlsByCategory", key = "#categoryId")
    public List<ProductGraphql> getProductGraphqlsByCategory(int categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        List<ProductGraphql> response = new ArrayList<>();
        Set<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toSet());

        // store
        Map<Long, List<StoreGraphql>> mapProductAndStoreGraphqls = searchCommon.getStoreGraphqls(productIds);

        // category
        CategoryGraphql categoryGraphql = searchCommon.getCategoryGraphql(categoryId);
        for (Product product : products) {
            response.add(searchCommon.getProductGraphqlFromProduct(product, categoryGraphql, mapProductAndStoreGraphqls.get(product.getId())));
        }
        return response;
    }

    private Predicate<ProductGraphql> byCategory(int categoryId) {
        return p -> p.getCategory().getId() == categoryId;
    }

    private Predicate<ProductGraphql> byPrice(FilterField filter) {
        int value = Integer.parseInt(filter.getValue());
        Predicate<ProductGraphql> byPrice = null;
        if (filter.getOperator().equals("eq")) {
            byPrice = p -> p.getPrice() == value;
        } else if (filter.getOperator().equals("lt")) {
            byPrice = p -> p.getPrice() < value;
        } else if (filter.getOperator().equals("le")) {
            byPrice = p -> p.getPrice() <= value;
        } else if (filter.getOperator().equals("gt")) {
            byPrice = p -> p.getPrice() > value;
        } else if (filter.getOperator().equals("ge")) {
            byPrice = p -> p.getPrice() >= value;
        }
        return byPrice;
    }
}
