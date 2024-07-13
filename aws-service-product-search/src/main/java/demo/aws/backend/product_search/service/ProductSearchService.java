package demo.aws.backend.product_search.service;

import demo.aws.backend.product.domain.entity.*;
import demo.aws.backend.product_search.graphql.filter.FilterField;
import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.graphql.response.*;
import demo.aws.backend.product_search.repository.*;
import lombok.extern.slf4j.Slf4j;
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

    public ProductGraphql getProductGraphqlById(long id) {
        ProductGraphql response = new ProductGraphql();
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            response = getProductGraphqlById(productOptional.get());
        }
        return response;
    }

    public ProductGraphql getProductGraphqlById(Product product) {
        ProductGraphql response = new ProductGraphql();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setImage(product.getImage());
        response.setColor(product.getColor());
        response.setMaterial(product.getMaterial());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStores(getStoreGraphqls(product.getId()));
        response.setCategory(getCategoryGraphql(product.getCategoryId()));
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

    private List<StoreGraphql> getStoreGraphqls(long productId) {
        List<StoreGraphql> response = new ArrayList<>();
        List<Integer> storeIds = productStoreRepository.findStoreIdOfProduct(productId);
        List<Store> storeList = storeRepository.findAllById(storeIds);

        // get cities
        Set<Integer> cityIds = storeList.stream().map(Store::getCityId).collect(Collectors.toSet());
        Map<Integer, CityGraphql> mapIdAndCityGraphql = getCityGraphqls(cityIds);

        for (Store store : storeList) {
            StoreGraphql storeGraphql = new StoreGraphql();
            storeGraphql.setId(store.getId());
            storeGraphql.setPhone(store.getPhone());
            storeGraphql.setAddress(store.getAddress());
            storeGraphql.setAddress2(store.getAddress2());
            storeGraphql.setDistrict(store.getDistrict());
            storeGraphql.setCity(mapIdAndCityGraphql.get(store.getCityId()));
            response.add(storeGraphql);
        }
        return response;
    }

    private CityGraphql getCityGraphql(int id) {
        CityGraphql response = new CityGraphql();
        Optional<City> cityOptional = cityRepository.findById(id);
        if(cityOptional.isPresent()) {
            City city = cityOptional.get();
            response.setId(city.getId());
            response.setName(city.getName());
            response.setCountry(getCountryGraphql(city.getCountryId()));
        }
        return response;
    }

    private Map<Integer, CityGraphql> getCityGraphqls(Set<Integer> cityIds) {
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

    private CountryGraphql getCountryGraphql(int id) {
        CountryGraphql response = new CountryGraphql();
        Optional<Country> countryOptional = countryRepository.findById(id);
        if(countryOptional.isPresent()) {
            Country country = countryOptional.get();
            response.setId(country.getId());
            response.setName(country.getName());
        }
        return response;
    }

    private Map<Integer, CountryGraphql> getCountryGraphqls(Set<Integer> countryIds) {
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

    public Iterable<ProductGraphql> productsWithFilter(@Argument ProductFilter filter) {
        // build filter
        Specification<Product> spec = buildFilter(filter);
        // run filter
        log.info("START QUERY {}", LocalDateTime.now());
        Page<Product> products = productRepository.findAll(spec, PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.by(Sort.Direction.ASC, "price")));
        log.info("END QUERY {}", LocalDateTime.now());
        // build response
        List<ProductGraphql> response = new ArrayList<>();
        for (Product product : products.stream().toList()) {
            response.add(getProductGraphqlById(product));
        }
        return response;
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
