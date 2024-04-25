package demo.aws.backend.order.service;

import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.backend.order.repository.OrderRepository;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessagingService {
    @Value("${custom.aws.sns.topic-order-process-arn}")
    private String TOPIC_ORDER_PROCESS;
    private final SqsTemplate sqsTemplate;
    private final SnsTemplate snsTemplate;
    private final OrderRepository orderRepository;

    public OrderMessagingService(SqsTemplate sqsTemplate, SnsTemplate snsTemplate, OrderRepository orderRepository) {
        this.sqsTemplate = sqsTemplate;
        this.snsTemplate = snsTemplate;
        this.orderRepository = orderRepository;
    }

    @Async
    public void sendOrderCreateEvent(Order order) {
        OrderProcessRequestDto event = new OrderProcessRequestDto();
        event.setCustomerId(order.getCustomerId());
        event.setOrderId(order.getId());
        event.setStatus(OrderStatus.CREATING);
        event.setRecipient(order.getRecipient());
        event.setItems(order.getItems());

        // send to sns
        if(StringUtils.isNotEmpty(TOPIC_ORDER_PROCESS)) {
            String subject = "create_order_" + order.getId();
            snsTemplate.sendNotification(TOPIC_ORDER_PROCESS, event, subject);
            changeOrderStatus(order.getId(), OrderStatus.CREATING);
        } else {
            log.error("value of TOPIC_ORDER_PROCESS is empty");
        }
    }
    @Async
    public void changeOrderStatus(String orderId, OrderStatus status) {
        var orderOpt = orderRepository.findById(orderId);
        if(orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            orderRepository.save(order);
        } else {
            log.error("order with id {} is not exist", orderId);
        }
    }
}
