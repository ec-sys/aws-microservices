package demo.aws.backend.order.domain.dto;

import demo.aws.backend.order.domain.model.OrderErrorCode;
import demo.aws.backend.order.domain.model.OrderStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderProcessResponeDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderId;
    private long customerId;
    private OrderStatus status;
    private OrderErrorCode errorCode;
}
