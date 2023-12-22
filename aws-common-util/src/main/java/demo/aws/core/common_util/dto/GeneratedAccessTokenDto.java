package demo.aws.core.common_util.dto;

import lombok.Data;

@Data
public class GeneratedAccessTokenDto {
    private String generatedToken;
    private long expireTime;
    private String generatedPrivateKey;
    private String generatedPublicKey;
}
