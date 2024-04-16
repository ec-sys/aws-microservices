package demo.aws.backend.post.service;

import demo.aws.backend.comment.domain.entity.Comment;
import demo.aws.backend.post.api.response.PostCommentDto;
import demo.aws.backend.post.rest.CommentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;

@Service
@Slf4j
public class PostService {

    @Autowired
    CommentClient commentClient;

    public PostCommentDto getPostWithComment(long postId) {
        PostCommentDto dto = new PostCommentDto();
        dto.setPostId(postId);
        List<Comment> comments = commentClient.findByPost(postId);
        dto.setCommentNumber(comments.size());
        dto.setCommentHostName(comments.get(0).getContent());

        String hostName = "NONE";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {
            hostName = ex.getMessage();
        }
        dto.setPostHostName(hostName);
        return dto;
    }
}
