package demo.aws.backend.product_search.service;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import demo.aws.backend.product_search.domain.entity.redis.*;
import demo.aws.backend.product_search.graphql.filter.FilterField;
import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.graphql.response.*;
import demo.aws.backend.product_search.repository.redis.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductHazelcastService {
    @Autowired
    CountryRedisRepository countryRedisRepository;
    @Autowired
    CityRedisRepository cityRedisRepository;
    @Autowired
    StoreRedisRepository storeRedisRepository;
    @Autowired
    ProductRedisRepository productRedisRepository;
    @Autowired
    CategoryRedisRepository categoryRedisRepository;
    ClientConfig clientConfig = new ClientConfig();

    public List<ProductGraphql> productsWithFilter(ProductFilter filter) {
        List<ProductGraphql> response = new ArrayList<>();
        int categoryId = filter.getCategoryId();
        int limit = filter.getPageSize();
        int offset = filter.getPageNumber() * limit;
        CategoryRedis categoryRedis = categoryRedisRepository.findById(categoryId).get();

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<Long, ProductRedis> mapProducts = client.getMap("products"); //creates the map proxy
        Map<Long, ProductRedis> productRedisMap = mapProducts.getAll(categoryRedis.getProductIds().stream().limit(limit).collect(Collectors.toSet()));

        List<ProductRedis> targetProducts = productRedisMap.values()
                .stream()
                .filter(buildFilter(filter))
                .sorted(Comparator.comparing(o -> o.getPrice()))
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());

        Set<Integer> storeIds = new HashSet<>();
        targetProducts.forEach(item -> {
            if(CollectionUtils.isNotEmpty(item.getStoreIds())) {
                storeIds.addAll(item.getStoreIds());
            }
        });
        Map<Integer, StoreGraphql> mapIdAndStoreGraphql = getStoreGraphqls(storeIds);

        CategoryGraphql categoryGraphql = getCategoryGraphql(categoryRedis);
        // build response
        targetProducts.forEach(product -> {
            List<StoreGraphql> storeGraphqls = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(product.getStoreIds())) {
                for (int storeId : product.getStoreIds()) {
                    storeGraphqls.add(mapIdAndStoreGraphql.get(storeId));
                }
            }
            response.add(getProductGraphqlFromProduct(product, categoryGraphql, storeGraphqls));
        });
        client.shutdown();
        return response;
    }

    private ProductGraphql getProductGraphqlFromProduct(ProductRedis product, CategoryGraphql categoryGraphql, List<StoreGraphql> storeGraphqls) {
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

    private CategoryGraphql getCategoryGraphql(CategoryRedis category) {
        CategoryGraphql response = new CategoryGraphql();
        response.setId(category.getId());
        response.setParentId(category.getParentId());
        response.setDescription(category.getDescription());
        response.setName(category.getName());
        return response;
    }

    public Map<Integer, StoreGraphql> getStoreGraphqls(Set<Integer> storeIds) {
        Map<Integer, StoreGraphql> response = new HashMap<>();

        // get stores
        List<StoreRedis> storeList = IteratorUtils.toList(storeRedisRepository.findAllById(storeIds).iterator());

        // get cities
        Set<Integer> cityIds = storeList.stream().map(StoreRedis::getCityId).collect(Collectors.toSet());
        Map<Integer, CityGraphql> mapIdAndCityGraphql = getCityGraphqls(cityIds);

        // store graphql from store
        for (StoreRedis store : storeList) {
            response.put(store.getId(), getStoreGraphqlFromStore(store, mapIdAndCityGraphql.get(store.getCityId())));
        }
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

    private Predicate<ProductRedis> buildFilter(ProductFilter filter) {
        Predicate<ProductRedis> spec = byCategory(filter.getCategoryId());
        if (Objects.nonNull(filter.getPrice())) {
            spec = spec.and(byPrice(filter.getPrice()));
        }
        return spec;
    }

    private Predicate<ProductRedis> byCategory(int categoryId) {
        return p -> p.getCategoryId() == categoryId;
    }

    private Predicate<ProductRedis> byPrice(FilterField filter) {
        int value = Integer.parseInt(filter.getValue());
        Predicate<ProductRedis> byPrice = null;
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
