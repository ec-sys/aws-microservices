package demo.aws.backend.post.service;

import demo.aws.backend.post.domain.entity.Post;
import demo.aws.backend.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        if(CollectionUtils.isEmpty(allPosts)) {
            Post post = new Post();
            post.setTitle("test title");
            post.setContent("test content");
            post.setDescription("test description");
            post.setViewCount(1);
            post.setPublic(true);
            postRepository.save(post);
        }
        return postRepository.findAll();
    }
}
