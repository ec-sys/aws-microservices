package demo.aws.sample.graphql.api.controller;

import demo.aws.sample.graphql.domain.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorDao {
    private final List<Author> authors;

    public AuthorDao(List<Author> authors) {
        this.authors = authors;
    }

    public Author getAuthor(String id) {
        return authors.stream()
                .filter(author -> id.equals(author.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
