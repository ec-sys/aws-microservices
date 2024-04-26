package demo.aws.backend.order.domain.entity;

import demo.aws.backend.order.domain.constant.OrderProcessStep;
import demo.aws.backend.order.domain.model.OrderErrorCode;
import demo.aws.core.framework.auditing.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "order_processes")
public class OrderStepResult extends Auditable<String> {
    @Id
    private String id;
    @Indexed
    private String orderId;
    @Indexed
    private String targetDate;
    private OrderProcessStep step;
    private OrderErrorCode errorCode;
    private String detail;
}
