package demo.aws.backend.batch.job;

import demo.aws.backend.batch.core.BatchResult;
import demo.aws.backend.batch.core.Job;
import demo.aws.backend.batch.core.JobRunner;
import demo.aws.backend.batch.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static demo.aws.backend.batch.core.JobType.Job000;

@Job(Job000)
@Slf4j
public class Job000Runner implements JobRunner {

    private BatchResult batchResult;

    @Override
    public void prepare() {
        batchResult = new BatchResult(this.getClass());
    }

    @Autowired
    TestService testService;

    @Override
    public void execute() throws Exception {
        testService.logJobName(Job000.name());
    }

    @Override
    public void onComplete() {
        batchResult.finish();
        log.info(batchResult.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        batchResult.finish();
        log.info(batchResult.toString());
        log.error("*** error {} {}", getClass().getSimpleName(), throwable);
    }
}
