package demo.aws.backend.customer.service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class OrderProcessService {
    private final SqsTemplate sqsTemplate;

    public OrderProcessService(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @SqsListener("order-customer-queue")
    public void listenToProcessCustomerOfOrder(String message) {
        log.info("{}", "i received " + message);
        sqsTemplate.send("order-process-queue", "i received " + message);
    }

//    @SqsListener("order-process-queue")
//    public void listenToResultProcessOrder(String message) {
//        log.info("{}", "i received " + message);
//        sqsTemplate.send("order-process-queue", "i received " + message);
//    }
}
