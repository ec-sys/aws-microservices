package demo.aws.backend.comment.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
