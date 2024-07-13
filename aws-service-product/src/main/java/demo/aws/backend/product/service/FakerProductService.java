package demo.aws.backend.product.service;

import com.github.javafaker.Faker;
import demo.aws.backend.product.domain.entity.Category;
import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product.domain.entity.ProductStore;
import demo.aws.backend.product.domain.entity.Store;
import demo.aws.backend.product.repository.CategoryRepository;
import demo.aws.backend.product.repository.ProductRepository;
import demo.aws.backend.product.repository.ProductStoreRepository;
import demo.aws.backend.product.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class FakerProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductStoreRepository productStoreRepository;
    @Autowired
    StoreRepository storeRepository;

    public void updateStoreToProduct() {
        Random random = new Random();
        List<Store> allStores = storeRepository.findAll();
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

            List<ProductStore> productStoreList = new ArrayList<>();
            for (Product product : products) {
                int storeNumber = random.nextInt(5);
                for (int j = 0; j < storeNumber; j++) {
                    ProductStore productStore = new ProductStore();
                    productStore.setProductId(product.getId());
                    productStore.setStoreId(allStores.get(random.nextInt(allStores.size() - 1)).getId());
                    productStoreList.add(productStore);
                }
            }
            productStoreRepository.saveAll(productStoreList);
            log.info("DONE PAGE {}", i);
        }
    }

    public void fakeCategories() {
        Faker faker = new Faker();
        List<String> readyNames = new ArrayList<>();
        // 10 x 5.000 = 50.000
        for (int i = 0; i < 10; i++) {
            List<Category> categories = new ArrayList<>();
            for (int j = 0; j < 5000; j++) {
                Category category = new Category();
                String cateName = StringUtils.EMPTY;
                int counter = 0;
                // try 10
                while (true) {
                    cateName = faker.company().industry();
                    if (!readyNames.contains(cateName)) {
                        break;
                    } else {
                        counter++;
                    }
                    if(counter == 10) {
                        break;
                    }
                }
                category.setName(cateName);
                category.setDescription(faker.company().name());
                categories.add(category);
                readyNames.add(cateName);
            }
            categoryRepository.saveAll(categories);
            log.info("DONE LOOP {}", i);
        }
    }

    public void fakeProducts() {
        Faker faker = new Faker();
        List<Category> categories = categoryRepository.findAll();
        int totalCate = categories.size();
        Random random = new Random();
        List<String> readyNames = new ArrayList<>();
        // 400 x 5.000 = 2.000.000
        for (int i = 0; i < 400; i++) {
            List<Product> products = new ArrayList<>();
            for (int j = 0; j < 5000; j++) {
                Product product = new Product();
                Category category = categories.get(random.nextInt(10000));
                String productName = StringUtils.EMPTY;
                int counter = 0;
                // try 5
                while (true) {
                    productName = faker.commerce().productName();
                    if (!readyNames.contains(productName)) {
                        break;
                    }
                    counter++;
                    if(counter == 5) {
                        productName = faker.book().title();
                        break;
                    }
                }
                product.setCategoryId(category.getId());
                product.setName(faker.commerce().productName());
                product.setColor(faker.commerce().color());
                product.setPrice(random.nextInt(1, 4000));
                product.setMaterial(faker.commerce().material());
                product.setDescription(faker.book().title());
                products.add(product);
            }
            productRepository.saveAll(products);
            log.info("DONE LOOP {}", i);
        }
    }
}
