package demo.aws.backend.inventory.service;

import com.google.gson.Gson;
import demo.aws.backend.order.domain.constant.OrderProcessConstant;
import demo.aws.backend.order.domain.constant.OrderProcessStep;
import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.model.OrderErrorCode;
import demo.aws.core.common_util.model.aws.AwsSqsMessageFromSns;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.QueueAttributes;
import io.awspring.cloud.sqs.listener.Visibility;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.services.sqs.model.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class OrderProcessService {
    private final SqsTemplate sqsTemplate;

    public OrderProcessService(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

//    @SqsListener(value = OrderProcessConstant.QUEUE_ORDER_INVENTORY)
//    public void listenToProcessCustomerOfOrder(OrderProcessRequestDto requestDto) {
//        log.info("{}", "i received order with id {}, status {}" + requestDto.getOrderId(), requestDto.getStatus());
//        // TODO: logic check customer
//        // return check result
//        OrderProcessResponseDto responseDto = new OrderProcessResponseDto();
//        responseDto.setOrderStatus(requestDto.getStatus());
//        responseDto.setOrderId(requestDto.getOrderId());
//        responseDto.setProcessStep(OrderProcessStep.INVENTORY);
//        responseDto.setErrorCode(OrderErrorCode.NONE);
//        sqsTemplate.send(OrderProcessConstant.QUEUE_ORDER_PROCESS, responseDto);
//    }

    @SqsListener(value = OrderProcessConstant.QUEUE_ORDER_INVENTORY)
    public void listenToProcessCustomerOfOrder(MessageHeaders headers, Visibility visibility, QueueAttributes queueAttributes, Message originalMessage) {
        String body = originalMessage.body();
        if(StringUtils.isNotBlank(body)) {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            AwsSqsMessageFromSns fromSns = gson.fromJson(body, AwsSqsMessageFromSns.class);
            if(StringUtils.isNotBlank(fromSns.getMessage())) {
                OrderProcessRequestDto requestDto = gson.fromJson(fromSns.getMessage(), OrderProcessRequestDto.class);
                int i = 10;
            }
        }
        int i = 10;
    }
}
