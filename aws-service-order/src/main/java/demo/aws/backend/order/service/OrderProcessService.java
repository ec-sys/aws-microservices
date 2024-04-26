package demo.aws.backend.order.service;

import demo.aws.backend.order.api.request.OrderCreateRequest;
import demo.aws.backend.order.api.response.OrderCreateResponse;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.entity.OrderStepResult;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.backend.order.repository.OrderRepository;
import demo.aws.backend.order.repository.OrderStepResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class OrderProcessService {
    private final OrderRepository orderRepository;
    private final OrderStepResultRepository orderStepResultRepository;
    private final OrderMessagingService orderMessagingService;

    public OrderProcessService(OrderRepository orderRepository, OrderStepResultRepository orderStepResultRepository, OrderMessagingService orderMessagingService) {
        this.orderRepository = orderRepository;
        this.orderStepResultRepository = orderStepResultRepository;
        this.orderMessagingService = orderMessagingService;
    }

    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        OrderCreateResponse response = new OrderCreateResponse();
        Order createOrder = orderRepository.save(mappingOrderCreateRequest(request));
        response.setOrderId(createOrder.getId());
        response.setStatus(createOrder.getStatus());

        // send create order event
        orderMessagingService.sendOrderCreateEvent(createOrder);

        return response;
    }

    private Order mappingOrderCreateRequest(OrderCreateRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setItems(request.getItems());
        order.setStatus(OrderStatus.NEW);
        order.setRecipient(request.getRecipient());
        return order;
    }

    public void saveResultProcessOrder(OrderProcessResponseDto responseDto) {
        OrderStepResult processResult = new OrderStepResult();
        processResult.setOrderId(responseDto.getOrderId());
        processResult.setStep(responseDto.getProcessStep());
        processResult.setErrorCode(responseDto.getErrorCode());
        processResult.setTargetDate(getUTCTargetDate());
        orderStepResultRepository.save(processResult);

    }

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

    private String getUTCTargetDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        return dateTime.format(formatter); // "1986-04-08 12:30"
    }
}
