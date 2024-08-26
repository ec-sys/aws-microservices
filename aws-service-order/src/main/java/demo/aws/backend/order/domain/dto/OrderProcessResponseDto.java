package demo.aws.backend.order.domain.dto;

import demo.aws.backend.order.domain.constant.OrderProcessStep;
import demo.aws.backend.order.domain.model.OrderErrorCode;
import demo.aws.backend.order.domain.model.OrderStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderProcessResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orderId;
    private OrderStatus orderStatus;
    private OrderErrorCode errorCode;
    private OrderProcessStep processStep;
}
