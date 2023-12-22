package demo.aws.core.common_util.dto;

import lombok.Data;

@Data
public class GeneratedRefreshTokenDto {
    private String generatedToken;
    private long expireTime;
}
