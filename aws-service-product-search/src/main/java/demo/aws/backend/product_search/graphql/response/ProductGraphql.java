package demo.aws.backend.product_search.graphql.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductGraphql {
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
