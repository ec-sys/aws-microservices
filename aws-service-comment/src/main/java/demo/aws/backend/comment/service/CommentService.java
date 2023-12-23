package demo.aws.backend.comment.service;

import demo.aws.backend.comment.api.response.CommentPostItem;
import demo.aws.backend.comment.domain.entity.Comment;
import demo.aws.backend.comment.repository.CommentRepository;
import demo.aws.backend.comment.rpc.PostGrpcClient;
import demo.aws.core.autogen.grpc.post.PSTPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostGrpcClient postGrpcClient;

    public List<Comment> getCommentsOfPost(long postId) {
        return commentRepository.findByPostId(postId);
    }

    public CommentPostItem getComment(long commentId) {
        CommentPostItem item = new CommentPostItem();
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if(commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            item.setCommentId(commentId);
            PSTPostResponse post = postGrpcClient.getPostById(comment.getPostId());
            item.setPostId(post.getPostId());
            item.setPostTitle(post.getTitle());
        } else {
            log.error("NOT EXIST COMMENT WITH ID: {}", commentId);
            throw new IllegalArgumentException("NOT EXIST COMMENT WITH ID");
        }
        return item;
    }
}
