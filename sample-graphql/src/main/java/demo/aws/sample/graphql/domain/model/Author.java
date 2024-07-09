package demo.aws.sample.graphql.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Author {

    private String id;
    private String name;
    private String thumbnail;
    private List<Post> posts;
}