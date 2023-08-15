package demo.aws.backend.post.rest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "comment-service")
public class CommentClient {
}
