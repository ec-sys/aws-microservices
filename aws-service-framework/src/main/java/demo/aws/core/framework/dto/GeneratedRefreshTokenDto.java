package demo.aws.core.framework.dto;

import lombok.Data;

@Data
public class GeneratedRefreshTokenDto {
    private String generatedToken;
    private long expireTime;
}
