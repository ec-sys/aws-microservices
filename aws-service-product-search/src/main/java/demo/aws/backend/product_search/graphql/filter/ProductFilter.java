package demo.aws.backend.product_search.graphql.filter;

import lombok.Data;

@Data
public class ProductFilter {
    private int categoryId;
    private FilterField name;
    private FilterField price;
    private FilterField color;
    private FilterField material;

    private int pageNumber;
    private int pageSize;
    private Boolean isDetail;
    private Boolean isCache;
}
