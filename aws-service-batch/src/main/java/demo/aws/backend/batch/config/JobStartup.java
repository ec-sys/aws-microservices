package demo.aws.backend.batch.config;

import demo.aws.backend.batch.core.JobRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static demo.aws.backend.batch.core.Constants.PROP_JOB_ID;

@Component
@Slf4j
public class JobStartup implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private Environment evn;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        try {
            String batchJobId = evn.getRequiredProperty(PROP_JOB_ID);
            if(StringUtils.isEmpty(batchJobId)) {
                log.error("batch id is required");
                return;
            }
            JobRunner jobRunner = (JobRunner) context.getBean(batchJobId.toLowerCase() + "Runner");
            jobRunner.prepare();
            try {
                jobRunner.execute();
            } catch (Exception ex) {
                jobRunner.onError(ex.getCause());
            }
            jobRunner.onComplete();
        } catch (Exception ex) {
            log.error(ExceptionUtils.getStackTrace(ex));
        }
    }
}
