package demo.aws.backend.product.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stores")
public class Store extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;
    private String address2;
    private int cityId;
    private String district;
    private String postalCode;
    private String phone;
    // private Point location;
}
