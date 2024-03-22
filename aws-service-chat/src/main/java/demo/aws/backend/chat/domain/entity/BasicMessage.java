package demo.aws.backend.chat.domain.entity;

import lombok.Data;

@Data
public class BasicMessage extends BasicEntity {
    private String text;
}
