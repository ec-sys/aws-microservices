package demo.aws.backend.order.domain.entity;

import demo.aws.backend.order.domain.model.OrderItem;
import demo.aws.backend.order.domain.model.OrderRecipient;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.core.framework.auditing.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "orders")
public class Order extends Auditable<String> {
    @Id
    private String id;
    @Indexed
    private long customerId;
    private OrderStatus status;
    private List<OrderItem> items;
    private OrderRecipient recipient;
}
