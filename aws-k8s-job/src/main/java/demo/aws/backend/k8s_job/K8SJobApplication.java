package demo.aws.backend.k8s_job;

import demo.aws.backend.k8s_job.service.CronJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K8SJobApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(K8SJobApplication.class, args);
    }

    @Autowired
    CronJobService cronJobService;

    @Override
    public void run(String... args) throws Exception {
        cronJobService.createJobFromCronJob("dev-envoy", "hello");
    }
}
