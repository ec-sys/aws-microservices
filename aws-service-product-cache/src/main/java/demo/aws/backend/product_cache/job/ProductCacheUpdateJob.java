package demo.aws.backend.product_cache.job;

import demo.aws.backend.product_cache.domain.constant.CacheNameConstant;
import demo.aws.backend.product_cache.job.model.ProductCacheUpdateItem;
import demo.aws.backend.product_cache.job.processor.ProductCacheUpdateProcessor;
import demo.aws.backend.product_cache.job.reader.ProductCacheUpdateReader;
import demo.aws.backend.product_cache.job.writer.ProductCacheUpdateWriter;
import demo.aws.backend.product_cache.repository.ProductRepository;
import demo.aws.backend.product_cache.service.ProductDataService;
import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductCacheUpdateJob {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    ProductDataService productDataService;

    @Bean
    public ItemReader<ProductCacheUpdateItem> reader() {
        return new ProductCacheUpdateReader(productRepository);
    }

    @Bean
    public ItemWriter<List<ProductGraphql>> writer() {
        return new ProductCacheUpdateWriter(cacheManager);
    }

    @Bean
    public ProductCacheUpdateProcessor processor() {
        return new ProductCacheUpdateProcessor(productDataService);
    }

    @Bean
    public void writerProductCacheUpdate(List<ProductGraphql> productGraphqls) {
        Cache cache = cacheManager.getCache(CacheNameConstant.PRODUCT_GRAPHQL);
        for (ProductGraphql item : productGraphqls) {
            cache.put(item.getId(), item);
        }
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      ItemReader<ProductCacheUpdateItem> reader, ProductCacheUpdateProcessor processor, ItemWriter<List<ProductGraphql>> writer) {
        return new StepBuilder("step1", jobRepository)
                .<ProductCacheUpdateItem, List<ProductGraphql>>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
