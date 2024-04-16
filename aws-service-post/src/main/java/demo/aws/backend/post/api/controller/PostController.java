package demo.aws.backend.post.api.controller;


import demo.aws.backend.post.api.response.PostCommentDto;
import demo.aws.backend.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post-mng")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/{postId}/with-comment")
    public ResponseEntity<PostCommentDto> getPostWithId(@PathVariable long postId) {
        return new ResponseEntity<>(postService.getPostWithComment(postId), HttpStatus.OK);
    }
}
