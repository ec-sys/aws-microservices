package demo.aws.backend.product_cache.job.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductCacheUpdateScheduler {

    // https://stackoverflow.com/questions/26147044/spring-cron-expression-for-every-day-101am
    // second, minute, hour, day of month, month, day(s) of week
    // cron = "0 15 10 15 * ?": executed at 10:15 AM on the 15th day of every month
    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Ho_Chi_Minh")
    public void updateProductCacheByCron() {
    }
}
