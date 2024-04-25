package demo.aws.backend.order.service;

import demo.aws.backend.order.api.request.OrderCreateRequest;
import demo.aws.backend.order.api.response.OrderCreateResponse;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.backend.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProcessService {
    private final OrderRepository orderRepository;
    private final OrderMessagingService orderMessagingService;

    public OrderProcessService(OrderRepository orderRepository, OrderMessagingService orderMessagingService) {
        this.orderRepository = orderRepository;
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
}
