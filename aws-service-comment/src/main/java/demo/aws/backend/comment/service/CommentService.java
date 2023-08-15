package demo.aws.backend.comment.service;

import demo.aws.backend.comment.domain.entity.Comment;
import demo.aws.backend.comment.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<Comment> getCommentsOfPost(long postId) {
        return commentRepository.findByPostId(postId);
    }
}
