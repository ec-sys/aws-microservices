package demo.aws.backend.order.service;

import demo.aws.backend.order.domain.constant.OrderProcessConstant;
import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.entity.OrderItem;
import demo.aws.backend.order.domain.entity.OrderRecipient;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.backend.order.domain.model.OutBoxOrderEventStatus;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderMessagingService {
    @Value("${custom.aws.sns.topic-order-process-arn}")
    private String TOPIC_ORDER_PROCESS;
    private final SqsTemplate sqsTemplate;
    private final SnsTemplate snsTemplate;

    @Lazy
    @Autowired
    private OrderProcessService orderProcessService;

    @Lazy
    @Autowired
    private OutBoxOrderService outBoxOrderService;

    public OrderMessagingService(SqsTemplate sqsTemplate, SnsTemplate snsTemplate) {
        this.sqsTemplate = sqsTemplate;
        this.snsTemplate = snsTemplate;
    }

    @Async
    @Transactional
    public void sendOrderCreateEvent(Order order, List<OrderItem> orderItems, OrderRecipient orderRecipient) {
        OrderProcessRequestDto event = new OrderProcessRequestDto();
        event.setCustomerId(order.getCustomerId());
        event.setOrderId(order.getId());
        event.setStatus(OrderStatus.NEW);
        event.setRecipient(orderRecipient);
        event.setItems(orderItems);

        // sending to sqs
        sendOrderCreateEventToAwsSqs(event);

        // changing out-box order status
        outBoxOrderService.changeOutBoxOrderStatus(event.getOrderId(), OutBoxOrderEventStatus.DONE);
    }

    // sending to aws sqs, after that it is processed by kafka in orchestrator_order service
    private void sendOrderCreateEventToAwsSqs(OrderProcessRequestDto event) {
        sqsTemplate.send(OrderProcessConstant.QUEUE_ORDER_CREATE, event);
        orderProcessService.changeOrderStatus(event.getOrderId(), OrderStatus.CREATING);
    }

    // sending to aws sqs, after that it is process by sqs in customer and inventory service
    private void sendOrderCreateEventToAwsSns(OrderProcessRequestDto event) {
        if(StringUtils.isNotEmpty(TOPIC_ORDER_PROCESS)) {
            String subject = "create_order_" + event.getOrderId();
            snsTemplate.sendNotification(TOPIC_ORDER_PROCESS, event, subject);
            orderProcessService.changeOrderStatus(event.getOrderId(), OrderStatus.CREATING);
        } else {
            log.error("value of TOPIC_ORDER_PROCESS is empty");
        }
    }

    @SqsListener(value = OrderProcessConstant.QUEUE_ORDER_PROCESS)
    public void listenToResultProcessOrder(OrderProcessResponseDto responseDto) {
        log.info("i received step check {}, error {}, with order id {}, with order status {}",
                responseDto.getProcessStep(), responseDto.getErrorCode(), responseDto.getOrderId(), responseDto.getOrderStatus());
        // TODO: aggregate check step to notify process order for shipping or notification
        orderProcessService.saveResultProcessOrder(responseDto);
    }
}
