package demo.aws.backend.product_cache.job;

import demo.aws.backend.product_cache.domain.constant.JobNameConstant;
import demo.aws.backend.product_cache.job.listener.ProductCacheUpdateListener;
import demo.aws.backend.product_cache.job.model.ProductCacheUpdateItem;
import demo.aws.backend.product_cache.job.processor.ProductCacheUpdateProcessor;
import demo.aws.backend.product_cache.job.reader.ProductCacheUpdateReader;
import demo.aws.backend.product_cache.job.tasklet.ProductCacheNotifyTasklet;
import demo.aws.backend.product_cache.job.writer.ProductCacheUpdateWriter;
import demo.aws.backend.product_cache.repository.ProductRepository;
import demo.aws.backend.product_cache.service.ProductDataService;
import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Component
public class ProductCacheUpdateJobConfig {

    @Value("${job.product-cache-update.chuck-size}")
    int chuckSize;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    ProductDataService productDataService;


    private ItemReader<ProductCacheUpdateItem> readerProductCache() {
        return new ProductCacheUpdateReader(productRepository);
    }


    private ItemWriter<List<ProductGraphql>> writerProductCache() {
        return new ProductCacheUpdateWriter(cacheManager);
    }

    private ProductCacheUpdateProcessor processorProductCache() {
        return new ProductCacheUpdateProcessor(productDataService);
    }

    public Step stepNotify(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        ProductCacheNotifyTasklet notifyTasklet = new ProductCacheNotifyTasklet();
        return new StepBuilder("step1-notify", jobRepository)
                .tasklet(notifyTasklet, transactionManager)
                .build();
    }


    public Step stepUpdate(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2-update", jobRepository)
                .<ProductCacheUpdateItem, List<ProductGraphql>>chunk(chuckSize, transactionManager)
                .reader(readerProductCache())
                .processor(processorProductCache())
                .writer(writerProductCache())
                .faultTolerant()
                .retryLimit(3)
                .retry(DeadlockLoserDataAccessException.class)
                .retry(JDBCConnectionException.class)
                .retry(Exception.class)
                .build();
    }

    @Bean
    public Job productCacheUpdateJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, ProductCacheUpdateListener listener) {
        Step stepNotify = stepNotify(jobRepository, transactionManager);
        Step stepUpdate = stepUpdate(jobRepository, transactionManager);
        return new JobBuilder(JobNameConstant.PRODUCT_CACHE_UPDATE_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepNotify)
                .next(stepUpdate)
                .build();
    }
}
