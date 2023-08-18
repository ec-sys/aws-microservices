package demo.aws.backend.uaa.api.response;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private String errorCode;
    private String accessToken;
    private Long accessTokenExpireTime;
    private String refreshToken;
    private Long refreshTokenExpireTime;
}