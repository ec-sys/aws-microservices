package demo.aws.backend.product_cache.job.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductCacheScheduler {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    Job productCacheUpdateJob;

    // https://stackoverflow.com/questions/26147044/spring-cron-expression-for-every-day-101am
    // second, minute, hour, day of month, month, day(s) of week
    // cron = "0 15 10 15 * ?": executed at 10:15 AM on the 15th day of every month
    // @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Ho_Chi_Minh")
    @Scheduled(cron = "0 2 16 * * *", zone = "Asia/Ho_Chi_Minh")
    public void updateProductCacheByCron() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("Start Job {}", productCacheUpdateJob.getName());
        JobExecution execution = jobLauncher.run(
                productCacheUpdateJob,
                new JobParametersBuilder().toJobParameters()
        );
        log.info("Exit status: {}", execution.getStatus());
    }
}
