package demo.aws.backend.product_cache.service;

import demo.aws.backend.product.domain.entity.*;
import demo.aws.backend.product_cache.repository.*;
import demo.aws.core.common_util.graphql.product.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductDataService {
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
    CacheManager cacheManager;
    public void upsertProductToCache() {
        int totalRecord = (int) productRepository.count();
        int limit = 2000;
        int pageSize = totalRecord / limit;
        if(pageSize * limit < totalRecord) {
            pageSize++;
        }
        log.info("TOTAL PAGE {}, LIMIT {}", pageSize, limit);
        for (int i = 0; i < pageSize; i++) {
            log.info("START PAGE {}", i);

            List<Long> productIds = productRepository.findAllIdPagination(limit, i * limit);
            log.info("DONE GET IDS");

            List<Product> products = productRepository.findAllById(productIds);
            log.info("DONE GET RECORDS");

            List<ProductGraphql> productGraphqls = getProductGraphqls(products);
            log.info("DONE GET ProductGraphql");

            Cache cache = cacheManager.getCache("productGraphqls");
            productGraphqls.forEach(item -> {
                cache.put(item.getId(), item);
            });
            log.info("DONE PAGE {}", i);
        }
    }

    public ProductGraphql getProductGraphqls(long productId) {
        Product product = productRepository.findById(productId).get();
        return getProductGraphqls(product);
    }

    public ProductGraphql getProductGraphqls(Product product) {
        if(Objects.isNull(product)) {
            return new ProductGraphql();
        }
        List<ProductGraphql> productGraphqls = getProductGraphqls(Arrays.asList(product));
        if(CollectionUtils.isNotEmpty(productGraphqls)) {
            return productGraphqls.get(0);
        } else {
            return new ProductGraphql();
        }
    }

    public List<ProductGraphql> getProductGraphqls(List<Product> products) {
        List<ProductGraphql> response = new ArrayList<>();
        Set<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toSet());
        Set<Integer> categoryIds = products.stream().map(Product::getCategoryId).collect(Collectors.toSet());

        // store
        Map<Long, List<StoreGraphql>> mapProductAndStoreGraphqls = getStoreGraphqls(productIds);

        // category
        Map<Integer, CategoryGraphql> mapIdAndCategoryGraphql = getCategoryGraphqls(categoryIds);

        for (Product product : products) {
            response.add(getProductGraphqlFromProduct(product, mapIdAndCategoryGraphql.get(product.getCategoryId()), mapProductAndStoreGraphqls.get(product.getId())));
        }
        return response;
    }

    public ProductGraphql getProductGraphqlFromProduct(Product product, CategoryGraphql categoryGraphql, List<StoreGraphql> storeGraphqls) {
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

    public Map<Integer, CategoryGraphql> getCategoryGraphqls(Set<Integer> categoryIds) {
        Map<Integer, CategoryGraphql> response = new HashMap<>();
        List<Category> categoryList = categoryRepository.findAllById(categoryIds);
        categoryList.forEach(category -> {
            CategoryGraphql categoryGraphql = new CategoryGraphql();
            categoryGraphql.setId(category.getId());
            categoryGraphql.setParentId(category.getParentId());
            categoryGraphql.setDescription(category.getDescription());
            categoryGraphql.setName(category.getName());
            response.put(category.getId(), categoryGraphql);
        });
        return response;
    }

    public CategoryGraphql getCategoryGraphql(int id) {
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

    public Map<Long, List<StoreGraphql>> getStoreGraphqls(Collection<Long> productIds) {
        Map<Long, List<StoreGraphql>> response = new HashMap<>();

        // get stores
        List<ProductStore> productStores = productStoreRepository.findByProductIdIn(productIds);
        Map<Long, List<ProductStore>> mapProductAndProductStores = productStores.stream().collect(Collectors.groupingBy(ProductStore::getProductId));

        Set<Integer> storeIds = productStores.stream().map(ProductStore::getStoreId).collect(Collectors.toSet());
        List<Store> storeList = storeRepository.findAllById(storeIds);

        // get cities
        Set<Integer> cityIds = storeList.stream().map(Store::getCityId).collect(Collectors.toSet());
        Map<Integer, CityGraphql> mapIdAndCityGraphql = getCityGraphqls(cityIds);

        // store graphql from store
        Map<Integer, StoreGraphql> mapIdAndStoreGraphql = new HashMap<>();
        for (Store store : storeList) {
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

    public StoreGraphql getStoreGraphqlFromStore(Store store, CityGraphql cityGraphql) {
        StoreGraphql storeGraphql = new StoreGraphql();
        storeGraphql.setId(store.getId());
        storeGraphql.setPhone(store.getPhone());
        storeGraphql.setAddress(store.getAddress());
        storeGraphql.setAddress2(store.getAddress2());
        storeGraphql.setDistrict(store.getDistrict());
        storeGraphql.setCity(cityGraphql);
        return storeGraphql;
    }

    public Map<Integer, CityGraphql> getCityGraphqls(Set<Integer> cityIds) {
        Map<Integer, CityGraphql> response = new HashMap<>();
        // get cities
        List<City> cities = cityRepository.findAllById(cityIds);
        // get countries graphql
        Set<Integer> countryIds = cities.stream().map(City::getCountryId).collect(Collectors.toSet());
        Map<Integer, CountryGraphql> mapIdAndCountryGraphql = getCountryGraphqls(countryIds);

        Map<Integer, City> mapIdAndCity = cities
                .stream()
                .collect(Collectors.toMap(City::getId, item -> item, (oldValue, newValue) -> newValue));
        mapIdAndCity.forEach((id, city) -> {
            CityGraphql cityGraphql = new CityGraphql();
            cityGraphql.setId(city.getId());
            cityGraphql.setName(city.getName());
            cityGraphql.setCountry(mapIdAndCountryGraphql.get(city.getCountryId()));
            response.put(id, cityGraphql);
        });
        return response;
    }

    public Map<Integer, CountryGraphql> getCountryGraphqls(Set<Integer> countryIds) {
        Map<Integer, CountryGraphql> response = new HashMap<>();
        Map<Integer, Country> mapIdAndCountry = countryRepository.findAllById(countryIds)
                .stream()
                .collect(Collectors.toMap(Country::getId, item -> item, (oldValue, newValue) -> newValue));
        mapIdAndCountry.forEach((id, country) -> {
            CountryGraphql countryGraphql = new CountryGraphql();
            countryGraphql.setId(country.getId());
            countryGraphql.setName(country.getName());
            response.put(id, countryGraphql);
        });
        return  response;
    }
}
