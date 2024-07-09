package demo.aws.sample.graphql.api.controller;

import demo.aws.backend.product_search.domain.model.Author;
import demo.aws.backend.product_search.domain.model.Post;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.List;

public class AuthorController {
    private final PostDao postDao;

    public AuthorController(PostDao postDao) {
        this.postDao = postDao;
    }

    @SchemaMapping
    public List<Post> posts(Author author) {
        return postDao.getAuthorPosts(author.getId());
    }
}
