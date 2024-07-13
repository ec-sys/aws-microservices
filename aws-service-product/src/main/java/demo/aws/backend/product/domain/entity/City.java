package demo.aws.backend.product.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cities")
public class City extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int countryId;
}
