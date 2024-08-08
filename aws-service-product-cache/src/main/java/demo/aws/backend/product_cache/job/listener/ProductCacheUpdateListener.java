package demo.aws.backend.product_cache.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCacheUpdateListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(ProductCacheUpdateListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
        } else {
            log.error("!!! JOB ERROR!");
        }
    }
}
