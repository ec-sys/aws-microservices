package demo.aws.backend.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.aws.backend.order.config.exception.OrderProcessingException;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.entity.OutBoxOrder;
import demo.aws.backend.order.domain.model.OutBoxOrderEventStatus;
import demo.aws.backend.order.domain.model.OutBoxOrderEventType;
import demo.aws.backend.order.repository.OutBoxOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OutBoxOrderService {
    @Autowired
    private OutBoxOrderRepository outBoxOrderRepository;

    @Transactional
    public void insertCreatedOrderEvent(Order order) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OutBoxOrder outBoxOrder = new OutBoxOrder();
            outBoxOrder.setOrderId(order.getId());
            outBoxOrder.setEventStatus(OutBoxOrderEventStatus.NEW);
            outBoxOrder.setEventType(OutBoxOrderEventType.CREATE_ORDER);
            outBoxOrder.setEventPayload(objectMapper.writeValueAsString(order));
            outBoxOrderRepository.save(outBoxOrder);
        } catch (JsonProcessingException e) {
            throw new OrderProcessingException("Error processing order event creation", e.getMessage());
        }
    }

    @Transactional
    public void changeOutBoxOrderStatus(long orderId, OutBoxOrderEventStatus status) {
        OutBoxOrder outBoxOrder = outBoxOrderRepository.findFirstByOrderId(orderId);
        outBoxOrder.setEventStatus(status);
        outBoxOrderRepository.save(outBoxOrder);
    }
}
