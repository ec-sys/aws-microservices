package demo.aws.backend.order.domain.entity;

import demo.aws.backend.order.domain.model.OutBoxOrderEventType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "outbox_orders")
public class OutBoxOrder {
    @Id
    private String id;
    private String orderId;
    private Order beforeOrder;
    private OutBoxOrderEventType eventType;
}
