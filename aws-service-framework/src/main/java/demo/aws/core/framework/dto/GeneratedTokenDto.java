package demo.aws.core.framework.dto;

import lombok.Data;

@Data
public class GeneratedTokenDto {
    private String generatedToken;
    private long expireTime;
}
