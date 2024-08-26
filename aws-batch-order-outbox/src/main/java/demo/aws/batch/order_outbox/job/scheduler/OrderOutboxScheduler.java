package demo.aws.batch.order_outbox.job.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OrderOutboxScheduler {
    // second, minute, hour, day of month, month, day(s) of week
    // @Scheduled(cron = "0 34 14 * * *", zone = "Asia/Ho_Chi_Minh")
    @Scheduled(fixedRateString = "${job.order-outbox.scheduled}", timeUnit = TimeUnit.SECONDS)
    public void updateProductCacheByCron() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("Start Job At Time {}", LocalDateTime.now());
    }
}
