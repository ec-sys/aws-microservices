package demo.aws.backend.comment.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "comments")
public class Comment extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private Long postId;
    private Long userId;

    private String content;
    private boolean isPublic;
}
