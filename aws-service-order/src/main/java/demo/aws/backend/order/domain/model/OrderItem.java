package demo.aws.backend.order.domain.model;

import lombok.Data;

@Data
public class OrderItem {
    private long productId;
    private int productPrice;
    private int quantity;
}
