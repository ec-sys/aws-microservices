package demo.aws.core.framework.dto;

import lombok.Data;

@Data
public class GeneratedAccessTokenDto {
    private String generatedToken;
    private long expireTime;
    private String generatedPrivateKey;
    private String generatedPublicKey;
}
