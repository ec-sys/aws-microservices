package demo.aws.backend.post.api.controller;


import demo.aws.backend.post.api.response.PostCommentDto;
import demo.aws.backend.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/get-all")
    public ResponseEntity<List<PostCommentDto>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }
}
