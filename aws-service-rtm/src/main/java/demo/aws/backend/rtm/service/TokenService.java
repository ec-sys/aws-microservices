package demo.aws.backend.rtm.service;

import demo.aws.backend.rtm.redis.UserToken;
import demo.aws.backend.rtm.redis.UserTokenRepository;
import demo.aws.core.common_util.JwtService;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
