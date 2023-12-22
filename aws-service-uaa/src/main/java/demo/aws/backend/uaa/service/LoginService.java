package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.api.request.LoginRequest;
import demo.aws.backend.uaa.api.request.RefreshTokenRequest;
import demo.aws.backend.uaa.api.response.LoginResponse;
import demo.aws.backend.uaa.api.response.RefreshTokenResponse;
import demo.aws.backend.uaa.domain.entity.User;
import demo.aws.backend.uaa.domain.entity.redis.UserToken;
import demo.aws.backend.uaa.domain.model.TokenInfo;
import demo.aws.backend.uaa.repository.RoleRepository;
import demo.aws.backend.uaa.repository.UserRepository;
import demo.aws.backend.uaa.repository.UserRoleRepository;
import demo.aws.backend.uaa.repository.redis.UserTokenRepository;
import demo.aws.core.common_util.JwtService;
import demo.aws.core.common_util.dto.GeneratedAccessTokenDto;
import demo.aws.core.common_util.dto.GeneratedRefreshTokenDto;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import demo.aws.core.framework.security.model.LoginInfo;
import demo.aws.core.framework.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LoginService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserTokenRepository userTokenRepository;

    public LoginResponse login(LoginRequest request) throws Exception {
        LoginResponse response = new LoginResponse();
        User loginUser = userRepository.findFirstByLoginId(request.getLoginId());
        if (Objects.isNull(loginUser) || !passwordEncoder.matches(request.getPassword(), loginUser.getPassword())) {
            log.error("{}-{} invalid user name or password", request.getLoginId(), request.getPassword());
            throw new IllegalArgumentException("Invalid user name or password");
        }
        log.info("{} pass valid login", request.getLoginId());

        JWTPayloadDto payloadDto = new JWTPayloadDto();
        payloadDto.setUserId(loginUser.getId());
        payloadDto.setLoginId(loginUser.getLoginId());
        payloadDto.setRoleNames(roleService.getRoleOfUser(loginUser.getId()));
        response.setToken(buildTokenInfo(payloadDto));

        // user data
        response.setUserId(loginUser.getId());
        response.setFirstName(loginUser.getFirstName());
        response.setLastName(loginUser.getLastName());

        return response;
    }

    private TokenInfo buildTokenInfo(JWTPayloadDto payloadDto) throws Exception {
        long targetTime = new Date().getTime();
        TokenInfo tokenInfo = new TokenInfo();

        // access token
        GeneratedAccessTokenDto accessToken = jwtService.generateAccessToken(payloadDto, targetTime, tokenInfo.getIdToken());
        tokenInfo.setAccessToken(accessToken.getGeneratedToken());
        tokenInfo.setAccessTokenExpireTime(accessToken.getExpireTime());

        // refresh token
        GeneratedRefreshTokenDto refreshToken = jwtService.generateRefreshToken(payloadDto.getLoginId(), targetTime, accessToken.getGeneratedPrivateKey());
        tokenInfo.setRefreshToken(refreshToken.getGeneratedToken());
        tokenInfo.setRefreshTokenExpireTime(refreshToken.getExpireTime());

        // save token to redis
        UUID uuid = UUID.randomUUID();
        UserToken userToken = new UserToken();
        userToken.setId(uuid.toString());
        userToken.setUserId(payloadDto.getUserId());
        userToken.setAccessToken(accessToken.getGeneratedToken());
        userToken.setPrivateKey(accessToken.getGeneratedPrivateKey());
        userToken.setPublicKey(accessToken.getGeneratedPublicKey());
        userToken.setRefreshToken(refreshToken.getGeneratedToken());
        userToken.setCreatedDate(new Date());
        userTokenRepository.save(userToken);

        tokenInfo.setIdToken(userToken.getId());
        return tokenInfo;
    }

    public LoginInfo buildLoginInfo(JWTPayloadDto payloadDto) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(payloadDto.getUserId());
        User user = userRepository.findById(payloadDto.getUserId());
        loginInfo.setFirstName(user.getFirstName());
        loginInfo.setLastName(user.getLastName());
        loginInfo.setFullName(CommonUtil.getFullName(user.getFirstName(), user.getLastName()));
        loginInfo.setRoleName(payloadDto.getRoleNames());
        return loginInfo;
    }

    public List<GrantedAuthority> getGrantedAuthorities(List<String> roleNames) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roleNames.forEach(roleName -> {
            authorities.add(new SimpleGrantedAuthority(roleName));
        });
        return authorities;
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws Exception {
        RefreshTokenResponse response = new RefreshTokenResponse();
        Optional<UserToken> userTokenOpt = userTokenRepository.findById(request.getTokenId());
        if (userTokenOpt.isEmpty()) {
            log.error("token isn't exits : {}", request.getTokenId());
            throw new IllegalArgumentException("invalid token");
        }

        UserToken userToken = userTokenOpt.get();
        if (request.getUserId() != userToken.getUserId() ||
                !userToken.getAccessToken().equals(request.getAccessToken()) ||
                !userToken.getRefreshToken().equals(request.getRefreshToken())) {
            log.error("token isn't valid with database : {}-{}-{}", request.getTokenId(), request.getAccessToken(), request.getRefreshToken());
            throw new IllegalArgumentException("invalid token");
        }

        JWTPayloadDto payloadDto = jwtService.getPayloadFromJWT(request.getAccessToken(), userToken.getPublicKey());
        TokenInfo tokenInfo = buildTokenInfo(payloadDto);

        response.setTokenId(tokenInfo.getIdToken());
        response.setAccessToken(tokenInfo.getAccessToken());
        response.setAccessTokenExpireTime(tokenInfo.getAccessTokenExpireTime());
        response.setRefreshToken(tokenInfo.getRefreshToken());
        response.setRefreshTokenExpireTime(tokenInfo.getRefreshTokenExpireTime());

        userTokenRepository.deleteById(userToken.getId());
        return response;
    }
}
