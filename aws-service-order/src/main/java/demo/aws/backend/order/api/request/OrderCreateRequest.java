package demo.aws.backend.order.api.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    private long customerId;
    private List<OrderItemDTO> items;
    private OrderRecipientDTO recipient;
}
