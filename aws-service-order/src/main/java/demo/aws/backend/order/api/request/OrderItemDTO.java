package demo.aws.backend.order.api.request;

import lombok.Data;

@Data
public class OrderItemDTO {
    private long productId;
    private int productPrice;
    private int quantity;
}
