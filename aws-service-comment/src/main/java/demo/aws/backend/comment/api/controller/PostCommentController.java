package demo.aws.backend.comment.api.controller;


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

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getOfPost(HttpServletRequest request, @PathVariable long postId) {
        return new ResponseEntity<>(commentService.getCommentsOfPost(postId), HttpStatus.OK);
    }

    @GetMapping("/check-post/{postId}")
    public ResponseEntity<String> testCloudLog(HttpServletRequest request, @PathVariable int postId) {
        String check = "";
        if(postId == 0) {
            check = "invalid";
            log.error("post id is {} {}", postId, check);
        } else {
            check = "valid";
            log.info("post id is {} {}", postId, check);
        }
        return new ResponseEntity<>(check, HttpStatus.OK);
    }
}