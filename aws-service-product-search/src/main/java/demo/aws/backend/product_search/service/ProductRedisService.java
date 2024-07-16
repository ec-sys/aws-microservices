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
    CountryRedisRepository countryRedisRepository;
    @Autowired
    CityRedisRepository cityRedisRepository;
    @Autowired
    StoreRedisRepository storeRedisRepository;

    @Autowired
    ProductStoreRepository productStoreRepository;

    public Map<Long, List<StoreGraphql>> getStoreGraphqls(Collection<Long> productIds) {
        List<ProductStore> productStores = productStoreRepository.findByProductIdIn(productIds);
        return getStoreGraphqls(productStores);
    }

    public Map<Long, List<StoreGraphql>> getStoreGraphqls(List<ProductStore> productStores) {
        Map<Long, List<StoreGraphql>> response = new HashMap<>();

        // get stores
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

    public StoreGraphql getStoreGraphqlFromStore(StoreRedis store, CityGraphql cityGraphql) {
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

    public Map<Integer, CountryGraphql> getCountryGraphqls(Set<Integer> countryIds) {
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
}
