package demo.aws.core.common_util.graphql.product;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryGraphql {
    private int id;
    private int parentId;
    private String name;
    private String description;
    List<ProductGraphql> products;
}
