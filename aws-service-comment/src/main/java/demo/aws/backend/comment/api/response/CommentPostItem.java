package demo.aws.backend.comment.api.response;

import lombok.Data;

@Data
public class CommentPostItem {
    private long commentId;
    private long postId;
}
