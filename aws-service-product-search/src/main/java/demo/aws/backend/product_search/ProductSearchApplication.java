package demo.aws.backend.product_search;

import com.hazelcast.client.config.ClientConfig;
import demo.aws.backend.product_search.service.ProductDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
public class ProductSearchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductSearchApplication.class, args);
    }

    @Autowired
    ProductDataService productDataService;

    ClientConfig clientConfig = new ClientConfig();
    @Override
    public void run(String... args) throws Exception {
//        productDataService.upsertCityFromDBToRedis();
//        log.info("DONE 1");
//        productDataService.upsertContryFromDBToRedis();
//        log.info("DONE 2");
//        productDataService.upsertStoreFromDBToRedis();
//        log.info("DONE 3");
//        productDataService.upsertCategoryFromDBToRedis();
//        log.info("DONE 4");
//        productDataService.upsertProductFromDBToRedis();
//        log.info("DONE 5");

//        productDataService.upsertContryFromDBToHazelcast();
//        log.info("DONE 1");
//        productDataService.upsertProductFromDBToHazelcast();
//        log.info("DONE 5");

//        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
//        IMap<Long, ProductRedis> mapProducts = client.getMap("products"); //creates the map proxy
//        mapProducts.addIndex(new IndexConfig(IndexType.HASH, "categoryId"));
//        log.info("DONE 5");
    }
}
