package demo.aws.backend.product_cache;

import demo.aws.backend.product_cache.service.ProductDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableBatchProcessing
public class ProductCacheApplication implements CommandLineRunner {

    @Autowired
    ProductDataService productDataService;
    @Autowired
    Job productCacheUpdateJob;
    @Autowired
    private JobLauncher jobLauncher;

    public static void main(String[] args) {
        SpringApplication.run(ProductCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // productDataService.upsertProductToCache();
        updateProductCacheJob();
    }

    public void updateProductCacheJob() throws Exception {
        log.info("Start Job {}", productCacheUpdateJob.getName());
        JobExecution execution = jobLauncher.run(
                productCacheUpdateJob,
                new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters()
        );
        log.info("Exit status: {}", execution.getStatus());
    }
}
