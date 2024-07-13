package demo.aws.backend.product_search.graphql.response;

import lombok.Data;

@Data
public class CategoryGraphql {
    private int id;
    private int parentId;
    private String name;
    private String description;
}
