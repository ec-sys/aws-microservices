package demo.aws.backend.product.api.response;

import lombok.Data;

import java.util.Date;

@Data
public class ProductItemResponse {
    private Long id;
    private int categoryId;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;
    private Date expiryDate;
}
