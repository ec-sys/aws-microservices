package demo.aws.backend.product_search.service;

import demo.aws.backend.product.domain.entity.Category;
import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product.domain.entity.ProductStore;
import demo.aws.backend.product_search.domain.entity.redis.CityRedis;
import demo.aws.backend.product_search.domain.entity.redis.CountryRedis;
import demo.aws.backend.product_search.domain.entity.redis.StoreRedis;
import demo.aws.backend.product_search.graphql.filter.FilterField;
import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.graphql.response.*;
import demo.aws.backend.product_search.repository.*;
import demo.aws.backend.product_search.repository.redis.CityRedisRepository;
import demo.aws.backend.product_search.repository.redis.CountryRedisRepository;
import demo.aws.backend.product_search.repository.redis.StoreRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductRedisService {
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
    CountryRedisRepository countryRedisRepository;
    @Autowired
    CityRedisRepository cityRedisRepository;
    @Autowired
    StoreRedisRepository storeRedisRepository;

    public List<ProductGraphql> getProductGraphqlsByCategoryIdWithDetail(List<Product> products, int categoryId) {
        List<ProductGraphql> response = new ArrayList<>();
        Set<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toSet());

        // store
        Map<Long, List<StoreGraphql>> mapProductAndStoreGraphqls = getStoreGraphqls(productIds);

        // category
        CategoryGraphql categoryGraphql = getCategoryGraphql(categoryId);
        for (Product product : products) {
            response.add(getProductGraphqlFromProduct(product, categoryGraphql, mapProductAndStoreGraphqls.get(product.getId())));
        }
        return response;
    }

    public List<ProductGraphql> getProductGraphqlsByCategoryId(List<Product> products, int categoryId) {
        List<ProductGraphql> response = new ArrayList<>();

        // store
        List<StoreGraphql> storeGraphqls = new ArrayList<>();

        // category
        CategoryGraphql categoryGraphql = getCategoryGraphql(categoryId);
        for (Product product : products) {
            response.add(getProductGraphqlFromProduct(product, categoryGraphql, storeGraphqls));
        }
        return response;
    }

    private ProductGraphql getProductGraphqlFromProduct(Product product, CategoryGraphql categoryGraphql, List<StoreGraphql> storeGraphqls) {
        ProductGraphql response = new ProductGraphql();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setImage(product.getImage());
        response.setColor(product.getColor());
        response.setMaterial(product.getMaterial());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStores(storeGraphqls);
        response.setCategory(categoryGraphql);
        return response;
    }

    private CategoryGraphql getCategoryGraphql(int id) {
        CategoryGraphql response = new CategoryGraphql();
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            response.setId(category.getId());
            response.setParentId(category.getParentId());
            response.setDescription(category.getDescription());
            response.setName(category.getName());
        }
        return response;
    }

    private Map<Long, List<StoreGraphql>> getStoreGraphqls(Collection<Long> productIds) {
        Map<Long, List<StoreGraphql>> response = new HashMap<>();

        // get stores
        List<ProductStore> productStores = productStoreRepository.findByProductIdIn(productIds);
        Map<Long, List<ProductStore>> mapProductAndProductStores = productStores.stream().collect(Collectors.groupingBy(ProductStore::getProductId));

        Set<Integer> storeIds = productStores.stream().map(ProductStore::getStoreId).collect(Collectors.toSet());
        List<StoreRedis> storeList = IteratorUtils.toList(storeRedisRepository.findAllById(storeIds).iterator());

        // get cities
        Set<Integer> cityIds = storeList.stream().map(StoreRedis::getCityId).collect(Collectors.toSet());
        Map<Integer, CityGraphql> mapIdAndCityGraphql = getCityGraphqls(cityIds);

        // store graphql from store
        Map<Integer, StoreGraphql> mapIdAndStoreGraphql = new HashMap<>();
        for (StoreRedis store : storeList) {
            mapIdAndStoreGraphql.put(store.getId(), getStoreGraphqlFromStore(store, mapIdAndCityGraphql.get(store.getCityId())));
        }

        // build map product graphql
        mapProductAndProductStores.forEach((id, stores) -> {
            List<StoreGraphql> storeGraphqls = new ArrayList<>();
            stores.forEach(item -> {
                storeGraphqls.add(mapIdAndStoreGraphql.get(item.getStoreId()));
            });
            response.put(id, storeGraphqls);
        });
        return response;
    }

    private StoreGraphql getStoreGraphqlFromStore(StoreRedis store, CityGraphql cityGraphql) {
        StoreGraphql storeGraphql = new StoreGraphql();
        storeGraphql.setId(store.getId());
        storeGraphql.setPhone(store.getPhone());
        storeGraphql.setAddress(store.getAddress());
        storeGraphql.setAddress2(store.getAddress2());
        storeGraphql.setDistrict(store.getDistrict());
        storeGraphql.setCity(cityGraphql);
        return storeGraphql;
    }

    private Map<Integer, CityGraphql> getCityGraphqls(Set<Integer> cityIds) {
        Map<Integer, CityGraphql> response = new HashMap<>();
        // get cities
        List<CityRedis> cities = IteratorUtils.toList(cityRedisRepository.findAllById(cityIds).iterator());
        // get countries graphql
        Set<Integer> countryIds = cities.stream().map(CityRedis::getCountryId).collect(Collectors.toSet());
        Map<Integer, CountryGraphql> mapIdAndCountryGraphql = getCountryGraphqls(countryIds);

        Map<Integer, CityRedis> mapIdAndCity = cities
                .stream()
                .collect(Collectors.toMap(CityRedis::getId, item -> item, (oldValue, newValue) -> newValue));
        mapIdAndCity.forEach((id, city) -> {
            CityGraphql cityGraphql = new CityGraphql();
            cityGraphql.setId(city.getId());
            cityGraphql.setName(city.getName());
            cityGraphql.setCountry(mapIdAndCountryGraphql.get(city.getCountryId()));
            response.put(id, cityGraphql);
        });
        return response;
    }

    private Map<Integer, CountryGraphql> getCountryGraphqls(Set<Integer> countryIds) {
        Map<Integer, CountryGraphql> response = new HashMap<>();
        Map<Integer, CountryRedis> mapIdAndCountry = IteratorUtils.toList(countryRedisRepository.findAllById(countryIds).iterator())
                .stream()
                .collect(Collectors.toMap(CountryRedis::getId, item -> item, (oldValue, newValue) -> newValue));
        mapIdAndCountry.forEach((id, country) -> {
            CountryGraphql countryGraphql = new CountryGraphql();
            countryGraphql.setId(country.getId());
            countryGraphql.setName(country.getName());
            response.put(id, countryGraphql);
        });
        return  response;
    }

    public Iterable<ProductGraphql> productsWithFilter(@Argument ProductFilter filter) {
        // build filter
        Specification<Product> spec = buildFilter(filter);

        // run filter
        log.info("START QUERY {}", LocalDateTime.now());
        Page<Product> products = productRepository.findAll(spec, PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.by(Sort.Direction.ASC, "price")));
        log.info("END QUERY {}", LocalDateTime.now());

        // build response
        if(filter.isDetail()) {
            return getProductGraphqlsByCategoryIdWithDetail(products.stream().toList(), filter.getCategoryId());
        } else {
            return getProductGraphqlsByCategoryId(products.stream().toList(), filter.getCategoryId());
        }
    }

    private Specification<Product> buildFilter(ProductFilter filter) {
        Specification<Product> spec = byCategory(filter.getCategoryId());
        if(Objects.nonNull(filter.getPrice())) {
            spec = spec.and(byPrice(filter.getPrice()));
        }
        if(Objects.nonNull(filter.getColor())) {
            spec = spec.and(byColor(filter.getColor()));
        }
        if(Objects.nonNull(filter.getMaterial())) {
            spec = spec.and(byMaterial(filter.getMaterial()));
        }
        return spec;
    }

    private Specification<Product> byCategory(int categoryId) {
        FilterField filterField = new FilterField("eq", String.valueOf(categoryId));
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("categoryId"));
    }
    private Specification<Product> byPrice(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("price"));
    }
    private Specification<Product> byColor(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("color"));
    }
    private Specification<Product> byMaterial(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("material"));
    }
}
