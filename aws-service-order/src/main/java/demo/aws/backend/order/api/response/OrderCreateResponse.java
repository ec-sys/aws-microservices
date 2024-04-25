package demo.aws.backend.order.api.response;

import demo.aws.backend.order.domain.model.OrderStatus;
import lombok.Data;

@Data
public class OrderCreateResponse {
    private String orderId;
    private OrderStatus status;
}
