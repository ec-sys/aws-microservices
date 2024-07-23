package demo.aws.core.common_util.graphql.product;

import lombok.Data;

import java.util.List;

@Data
public class CityGraphql {
    private int id;
    private String name;
    private List<StoreGraphql> stores;
    private CountryGraphql country;
}
