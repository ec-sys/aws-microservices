package demo.aws.backend.order.domain.dto;

import demo.aws.backend.order.domain.entity.OrderItem;
import demo.aws.backend.order.domain.entity.OrderRecipient;
import demo.aws.backend.order.domain.model.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderProcessRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private long orderId;
    private long customerId;
    private OrderStatus status;
    private List<OrderItem> items;
    private OrderRecipient recipient;
}
