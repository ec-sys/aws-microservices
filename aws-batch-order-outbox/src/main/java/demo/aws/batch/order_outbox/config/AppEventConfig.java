package demo.aws.batch.order_outbox.config;

import demo.aws.backend.order.domain.entity.OutBoxOrder;
import demo.aws.batch.order_outbox.repository.OutBoxOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppEventConfig implements CommandLineRunner {

    @Autowired
    OutBoxOrderRepository outBoxOrderRepository;
    @Autowired
    private JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {
        OutBoxOrder outBoxOrder = outBoxOrderRepository.findFirstByOrderId(2);
        int x = 1;
    }
}
