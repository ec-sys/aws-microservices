package demo.aws.backend.customer.service;

import demo.aws.backend.order.domain.constant.OrderProcessConstant;
import demo.aws.backend.order.domain.constant.OrderProcessStep;
import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.model.OrderErrorCode;
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

    @SqsListener(value = OrderProcessConstant.QUEUE_ORDER_CUSTOMER)
    public void listenToProcessCustomerOfOrder(OrderProcessRequestDto requestDto) {
        log.info("{}", "i received order with id {}, status {}" + requestDto.getOrderId(), requestDto.getStatus());
        // TODO: logic check customer
        // return check result
        OrderProcessResponseDto responseDto = new OrderProcessResponseDto();
        responseDto.setOrderStatus(requestDto.getStatus());
        responseDto.setOrderId(requestDto.getOrderId());
        responseDto.setProcessStep(OrderProcessStep.CUSTOMER);
        responseDto.setErrorCode(OrderErrorCode.NONE);
        sqsTemplate.send(OrderProcessConstant.QUEUE_ORDER_PROCESS, responseDto);
    }
}
