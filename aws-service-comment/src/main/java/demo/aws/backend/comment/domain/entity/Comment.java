package demo.aws.backend.comment.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
