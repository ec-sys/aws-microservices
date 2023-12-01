package demo.aws.backend.uaa.api.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String tokenId;
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
