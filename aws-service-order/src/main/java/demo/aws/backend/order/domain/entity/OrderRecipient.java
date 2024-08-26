package demo.aws.backend.order.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order-recipients")
public class OrderRecipient extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String userName;
    private String phoneNumber;
    private String email;
    private int proviceId;
    private int districtId;
    private int wardId;
    private String detail;
}
