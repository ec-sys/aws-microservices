package demo.aws.backend.product.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_stores")
public class ProductStore extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private long productId;
    private int storeId;
}
