package demo.aws.backend.comment.service;

import demo.aws.backend.comment.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class CommentService {
    public List<Comment> getCommentsOfPost(long postId) {
        String hostName = "NONE";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {
            hostName = ex.getMessage();
        }

        var response = new ArrayList<Comment>();
        Random random = new Random();
        int numberComment = random.nextInt(1, 100);
        for (int i = 0; i < numberComment; i++) {
            Comment comment = new Comment();
            comment.setPostId(postId);
            comment.setId(random.nextLong(1000L));
            comment.setContent(hostName);
            response.add(comment);
        }
        return response;
    }
}
