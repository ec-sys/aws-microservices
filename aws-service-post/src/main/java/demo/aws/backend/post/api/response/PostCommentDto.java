package demo.aws.backend.post.api.response;

import lombok.Data;

@Data
public class PostCommentDto {
    private long postId;
    private int commentNumber;
    private String commentHostName;
    private String postHostName;
}
