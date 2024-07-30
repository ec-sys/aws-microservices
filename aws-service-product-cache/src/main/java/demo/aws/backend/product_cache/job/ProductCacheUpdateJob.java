package demo.aws.backend.product_cache.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class ProductCacheUpdateJob {
//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Coffee> writer, VirtualThreadTaskExecutor taskExecutor) {
//        return new StepBuilder("step1", jobRepository)
//                .<Coffee, Coffee> chunk(10, transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer)
//                .taskExecutor(taskExecutor)
//                .build();
//    }
}
