package demo.aws.backend.api_gateway.service;

import demo.aws.backend.api_gateway.redis.UserToken;
import demo.aws.backend.api_gateway.redis.UserTokenRepository;
import demo.aws.core.common_util.JwtService;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import demo.aws.core.common_util.model.Role;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class TokenService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserTokenRepository userTokenRepository;

    public JWTPayloadDto getPayloadFromToken(String tokenId, String token) throws Exception {
        if (StringUtils.isEmpty(tokenId) || StringUtils.isEmpty(token)) {
            log.error("token is empty {} - {}", tokenId, token);
            throw new IllegalArgumentException("token is empty");
        }
        Optional<UserToken> userTokenOpt = userTokenRepository.findById(tokenId);
        if (userTokenOpt.isEmpty()) {
            log.error("token isn't exist in database {} - {}", tokenId, token);
            throw new IllegalArgumentException("token is not exist");
        }
        UserToken userToken = userTokenOpt.get();
        if (!token.equals(userToken.getAccessToken())) {
            log.error("token isn't valid {} - {} - {}", tokenId, token, userToken.getAccessToken());
            throw new IllegalArgumentException("token is not valid");
        }
        return jwtService.getPayloadFromJWT(token, userToken.getPublicKey());
    }

    public JWTPayloadDto createGuestPayload() {
        JWTPayloadDto payloadDto = new JWTPayloadDto();
        payloadDto.setLoginId("guest_user");
        payloadDto.setUserId(-1);
        payloadDto.setRoleNames(Arrays.asList(Role.GUEST.name()));
        return payloadDto;
    }
}
