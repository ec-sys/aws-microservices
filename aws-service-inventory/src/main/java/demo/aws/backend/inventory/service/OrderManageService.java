package demo.aws.backend.inventory.service;

import demo.aws.backend.order.domain.constant.OrderProcessConstant;
import demo.aws.backend.order.domain.constant.OrderProcessStep;
import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.model.OrderErrorCode;
import demo.aws.backend.order.domain.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderManageService {

    @Autowired
    private KafkaTemplate<String, OrderProcessResponseDto> kafkaTemplate;

    @KafkaListener(id = "create-orders", topics = "create-orders", groupId = "inventory")
    public void onCreateOrderEvent(OrderProcessRequestDto requestDto) {
        log.info("Received: {}" , requestDto);
        if(OrderStatus.NEW.equals(requestDto.getStatus())) {
            reserve(requestDto);
        } else {
            confirm(requestDto);
        }
    }

    public void reserve(OrderProcessRequestDto requestDto) {
        // TODO: logic check customer
        // send back to kafka after check
        OrderProcessResponseDto responseDto = new OrderProcessResponseDto();
        responseDto.setOrderStatus(requestDto.getStatus());
        responseDto.setOrderId(requestDto.getOrderId());
        responseDto.setOrderStatus(requestDto.getStatus());
        responseDto.setProcessStep(OrderProcessStep.INVENTORY);
        responseDto.setErrorCode(OrderErrorCode.NONE);
        kafkaTemplate.send(OrderProcessConstant.TOPIC_ORDER_INVENTORY, requestDto.getOrderId(), responseDto);
        log.info("Sent: {}", responseDto);
    }

    public void confirm(OrderProcessRequestDto requestDto) {
    }
}
