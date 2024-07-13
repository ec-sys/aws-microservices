package demo.aws.backend.product_search.graphql.filter;

import lombok.Data;

@Data
public class ProductFilter {
    private FilterField name;
    private FilterField price;
    private FilterField color;
    private FilterField material;
}
