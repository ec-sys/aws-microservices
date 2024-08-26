package demo.aws.backend.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.aws.backend.order.api.request.OrderCreateRequest;
import demo.aws.backend.order.api.request.OrderRecipientDTO;
import demo.aws.backend.order.api.response.OrderCreateResponse;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.entity.OrderItem;
import demo.aws.backend.order.domain.entity.OrderRecipient;
import demo.aws.backend.order.domain.entity.OrderStepResult;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.backend.order.repository.OrderItemRepository;
import demo.aws.backend.order.repository.OrderRecipientRepository;
import demo.aws.backend.order.repository.OrderRepository;
import demo.aws.backend.order.repository.OrderStepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderProcessService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRecipientRepository orderRecipientRepository;
    @Autowired
    private OrderStepRepository orderStepResultRepository;

    @Autowired
    private OrderMessagingService orderMessagingService;
    @Autowired
    private OutBoxOrderService outBoxOrderService;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        OrderCreateResponse response = new OrderCreateResponse();
        // to insert order
        Order createOrder = orderRepository.save(mappingOrderFromRequest(request));

        // to insert order items
        List<OrderItem> orderItems = mappingOrderItemsFromRequest(request);
        orderItems.forEach(orderItem -> orderItem.setOrderId(createOrder.getId()));
        orderItemRepository.saveAll(orderItems);

        // to insert order recipient
        OrderRecipient orderRecipient = mappingOrderRecipientFromRequest(request);
        orderRecipient.setOrderId(createOrder.getId());
        orderRecipientRepository.save(orderRecipient);

        // to save out-box order
        outBoxOrderService.insertCreatedOrderEvent(createOrder);

        // to send create order event
        orderMessagingService.sendOrderCreateEvent(createOrder, orderItems, orderRecipient);

        // response
        response.setOrderId(createOrder.getId());
        response.setStatus(createOrder.getStatus());

        return response;
    }

    private Order mappingOrderFromRequest(OrderCreateRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    private List<OrderItem> mappingOrderItemsFromRequest(OrderCreateRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        request.getItems().forEach(itemDTO -> {
            OrderItem item = new OrderItem();
            item.setQuantity(itemDTO.getQuantity());
            item.setProductId(itemDTO.getProductId());
            item.setProductPrice(itemDTO.getProductPrice());
            orderItems.add(item);
        });
        return orderItems;
    }

    private OrderRecipient mappingOrderRecipientFromRequest(OrderCreateRequest request) {
        OrderRecipientDTO recipient = request.getRecipient();

        // personal information
        OrderRecipient orderRecipient = new OrderRecipient();
        orderRecipient.setUserName(recipient.getUserName());
        orderRecipient.setEmail(recipient.getEmail());

        // address
        orderRecipient.setPhoneNumber(recipient.getPhoneNumber());
        orderRecipient.setProviceId(recipient.getProviceId());
        orderRecipient.setDistrictId(recipient.getDistrictId());
        orderRecipient.setWardId(recipient.getWardId());
        orderRecipient.setDetail(recipient.getDetail());

        return orderRecipient;
    }

    @Transactional
    public void saveResultProcessOrder(OrderProcessResponseDto responseDto) {
        OrderStepResult processResult = new OrderStepResult();
        processResult.setOrderId(responseDto.getOrderId());
        processResult.setStep(responseDto.getProcessStep());
        processResult.setErrorCode(responseDto.getErrorCode());
        orderStepResultRepository.save(processResult);
    }

    public void changeOrderStatus(Long orderId, OrderStatus status) {
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
