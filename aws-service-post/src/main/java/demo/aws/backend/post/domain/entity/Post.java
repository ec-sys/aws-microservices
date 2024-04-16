package demo.aws.backend.post.domain.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Post {
    private Long id;

    private String title;
    private String content;
    private boolean isPublic;
    private String description;
    private Integer viewCount;

    private Date created;
    private String creator;
    private Date updated;
    private String updater;
}