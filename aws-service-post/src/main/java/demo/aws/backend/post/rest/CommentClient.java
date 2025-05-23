package demo.aws.backend.post.rest;

import demo.aws.backend.comment.domain.entity.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-comment")
public interface CommentClient {

    @GetMapping("/post-comment/{postId}")
    List<Comment> findByPost(@PathVariable("postId") Long postId);
}