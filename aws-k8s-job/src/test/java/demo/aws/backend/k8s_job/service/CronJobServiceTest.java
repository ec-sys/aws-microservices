package demo.aws.backend.k8s_job.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CronJobServiceTest {
    @Autowired
    CronJobService cronJobService;
    @Test
    void testListAllCronJob() throws Exception {
        cronJobService.testConnect();
        cronJobService.listAllCronJob("dev-envoy");
    }
}
