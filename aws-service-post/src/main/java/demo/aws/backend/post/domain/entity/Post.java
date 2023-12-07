package demo.aws.backend.post.domain.entity;

import demo.aws.core.framework.auditing.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
@Entity
@Data
@Table(name = "posts")
public class Post extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
