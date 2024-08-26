package demo.aws.backend.order.domain.entity;

import demo.aws.backend.order.domain.model.OutBoxOrderEventStatus;
import demo.aws.backend.order.domain.model.OutBoxOrderEventType;
import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Indexed;

@Entity
@Data
@Table(name = "OUT-BOX-ORDERS", indexes = {
        @Index(name = "idx_order", columnList = "orderId", unique = true),
        @Index(name = "idx_target_date_status", columnList = "targetDate, eventStatus DESC")
})
public class OutBoxOrder extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String targetDate;
    private OutBoxOrderEventType eventType;
    private OutBoxOrderEventStatus eventStatus;
    private String eventPayload;
}
