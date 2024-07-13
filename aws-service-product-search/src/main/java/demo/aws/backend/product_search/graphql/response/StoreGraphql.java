package demo.aws.backend.product_search.graphql.response;

import lombok.Data;

import java.util.List;

@Data
public class StoreGraphql {
    private int id;
    private String address;
    private String address2;
    private String district;
    private String postalCode;
    private String phone;
    private List<ProductGraphql> products;
    private CityGraphql city;
}
