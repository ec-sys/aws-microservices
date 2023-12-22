package demo.aws.backend.comment.api.controller;


import demo.aws.backend.comment.api.response.CommentPostItem;
import demo.aws.backend.comment.domain.entity.Comment;
import demo.aws.backend.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post-comment")
@Slf4j
public class PostCommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<Comment>> getOfPost(@PathVariable long postId) {
        return new ResponseEntity<>(commentService.getCommentsOfPost(postId), HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentPostItem> getComment(@PathVariable long commentId) {
        return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
    }
}
