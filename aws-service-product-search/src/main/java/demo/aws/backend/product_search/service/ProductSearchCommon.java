package demo.aws.backend.product_search.service;

import demo.aws.backend.product.domain.entity.*;
import demo.aws.backend.product_search.graphql.filter.FilterField;
import demo.aws.backend.product_search.graphql.response.*;
import demo.aws.backend.product_search.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductSearchCommon {
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
