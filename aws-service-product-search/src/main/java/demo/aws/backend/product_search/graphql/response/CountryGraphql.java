package demo.aws.backend.product_search.graphql.response;

import lombok.Data;

import java.util.List;

@Data
public class CountryGraphql {
    private int id;
    private String name;
    private List<CityGraphql> cities;
}
