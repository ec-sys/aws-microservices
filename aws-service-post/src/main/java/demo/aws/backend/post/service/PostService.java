package demo.aws.backend.post.service;

import demo.aws.backend.post.api.response.PostCommentDto;
import demo.aws.backend.post.domain.entity.Post;
import demo.aws.backend.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostCommentDto> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        List<PostCommentDto> dtoList = new ArrayList<>();
        allPosts.forEach(post -> {
            PostCommentDto dto = new PostCommentDto();
            dto.setPostId(post.getId());
            dto.setCommentNumber(0);
            dtoList.add(dto);
        });
        return dtoList;
    }
}
