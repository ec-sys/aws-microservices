package demo.aws.backend.product_search.graphql.response;

import lombok.Data;

@Data
public class ProductGraphql {
    private long id;
    private String name;
    private String image;
    private int price;
    private String color;
    private String material;
    private String description;

    private int categoryId;
    private String categoryName;

    private int storeId;
    private int storeName;

    private int cityId;
    private String cityName;

    private int countryId;
    private String countryName;
}
