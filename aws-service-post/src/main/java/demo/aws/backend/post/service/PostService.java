package demo.aws.backend.post.service;

import demo.aws.backend.comment.domain.entity.Comment;
import demo.aws.backend.post.api.response.PostCommentDto;
import demo.aws.backend.post.domain.entity.Post;
import demo.aws.backend.post.repository.PostRepository;
import demo.aws.backend.post.rest.CommentClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {

    PostRepository postRepository;
    CommentClient commentClient;

    public PostService(PostRepository postRepository, CommentClient commentClient) {
        this.postRepository = postRepository;
        this.commentClient = commentClient;
    }

    public List<PostCommentDto> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        List<PostCommentDto> dtoList = new ArrayList<>();
        allPosts.forEach(post -> {
            PostCommentDto dto = new PostCommentDto();
            dto.setPostId(post.getId());
            List<Comment> comments = commentClient.findByPost(post.getId());
            dto.setCommentNumber(comments.size());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
