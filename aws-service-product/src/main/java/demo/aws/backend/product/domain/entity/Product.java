package demo.aws.backend.product.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "products")
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int categoryId;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;
}
