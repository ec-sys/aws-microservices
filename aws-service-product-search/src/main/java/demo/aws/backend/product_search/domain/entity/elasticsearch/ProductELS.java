package demo.aws.backend.product_search.domain.entity.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "products")
@Data
public class ProductELS {
    @Id
    private long id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Nested, name = "storeIds")
    private List<Integer> storeIds;

    @Field(type = FieldType.Integer, name = "price")
    private double price;

    @Field(type = FieldType.Text, name = "color")
    private String color;

    @Field(type = FieldType.Text, name = "material")
    private String material;

    @Field(type = FieldType.Text, name = "description")
    private String description;
}
