package demo.aws.backend.product_search.graphql.response;

import lombok.Data;

@Data
public class StoreGraphql {
    private int id;
    private String address;
    private String address2;
    private int cityId;
    private String district;
    private String postalCode;
    private String phone;
}
