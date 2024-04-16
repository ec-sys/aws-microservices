package demo.aws.backend.comment.domain.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    private Long id;

    private Long parentId;

    private Long postId;
    private Long userId;

    private String content;
    private boolean isPublic;

    private Date created;
    private String creator;
    private Date updated;
    private String updater;
}
