package demo.aws.backend.order.service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class OrderResultService {
    private final SqsTemplate sqsTemplate;

    public OrderResultService(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @SqsListener("order-process-queue")
    public void listenToResultProcessOrder(String message) {
        log.info("{}", "i received " + message);
        sqsTemplate.send("order-process-queue", "i received " + message);
    }
}
