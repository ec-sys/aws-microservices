package demo.aws.backend.order.api.request;

import demo.aws.backend.order.domain.model.OrderItem;
import demo.aws.backend.order.domain.model.OrderRecipient;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    private long customerId;
    private List<OrderItem> items;
    private OrderRecipient recipient;
}
