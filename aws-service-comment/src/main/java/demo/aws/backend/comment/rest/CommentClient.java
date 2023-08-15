package demo.aws.backend.comment.rest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "service-comment")
public class CommentClient {
}
