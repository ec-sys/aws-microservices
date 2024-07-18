package demo.aws.core.common_util.graphql.product;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductGraphql implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;

    private CategoryGraphql category;
    private List<StoreGraphql> stores;
}
