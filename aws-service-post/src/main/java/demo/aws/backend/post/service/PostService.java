package demo.aws.backend.post.service;

import demo.aws.backend.post.api.response.PostCommentDto;
import demo.aws.backend.post.domain.entity.Post;
import demo.aws.backend.post.repository.PostRepository;
import demo.aws.core.autogen.grpc.post.PSTPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public PSTPostResponse getPostById(long postId) {
        try {
            log.info("container ip: {}", InetAddress.getLocalHost().getHostName());
        } catch (Exception ex) {
            log.info("exception : {}", ex.getMessage());
        }

        Optional<Post> postOpt = postRepository.findById(postId);
        if(postOpt.isPresent()) {
            Post post = postOpt.get();
            return PSTPostResponse.newBuilder()
                    .setPostId(post.getId())
                    .setTitle(post.getTitle())
                    .build();
        } else {
            return PSTPostResponse.getDefaultInstance();
        }
    }
}
