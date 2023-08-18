package demo.aws.backend.uaa.service;

import demo.aws.backend.uaa.api.request.LoginRequest;
import demo.aws.backend.uaa.api.request.RefreshTokenRequest;
import demo.aws.backend.uaa.api.response.LoginResponse;
import demo.aws.backend.uaa.api.response.RefreshTokenResponse;
import demo.aws.backend.uaa.domain.entity.User;
import demo.aws.backend.uaa.domain.model.TokenInfo;
import demo.aws.backend.uaa.repository.RoleRepository;
import demo.aws.backend.uaa.repository.UserRepository;
import demo.aws.backend.uaa.repository.UserRoleRepository;
import demo.aws.core.framework.constant.ErrorConstant;
import demo.aws.core.framework.dto.GeneratedTokenDto;
import demo.aws.core.framework.dto.JWTPayloadDto;
import demo.aws.core.framework.security.JwtService;
import demo.aws.core.framework.security.model.LoginInfo;
import demo.aws.core.framework.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public LoginResponse login(LoginRequest request) {

        LoginResponse response = new LoginResponse();
        User loginUser = userRepository.findFirstByLoginId(request.getLoginId());
        if (Objects.isNull(loginUser) || !passwordEncoder.matches(request.getPassword(), loginUser.getPassword())) {
            throw new IllegalArgumentException("Invalid user name or password");
        }
        log.info("{} pass valid login user", request.getLoginId());

        JWTPayloadDto payloadDto = new JWTPayloadDto();
        payloadDto.setUserId(loginUser.getId());
        payloadDto.setRoleNames(roleService.getRoleOfUser(loginUser.getId()));
        response.setToken(buildTokenInfo(payloadDto));

        // user data
        response.setUserId(loginUser.getId());
        response.setFirstName(loginUser.getFirstName());
        response.setLastName(loginUser.getLastName());

        return response;
    }

    private TokenInfo buildTokenInfo(JWTPayloadDto payloadDto) {
        long targetTime = new Date().getTime();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setIdToken(UUID.randomUUID().toString());

        // access token
        GeneratedTokenDto tokenDto = jwtService.generateAccessToken(payloadDto, targetTime, tokenInfo.getIdToken());
        tokenInfo.setAccessToken(tokenDto.getGeneratedToken());
        tokenInfo.setAccessTokenExpireTime(tokenDto.getExpireTime());

        // refresh token
        tokenDto = jwtService.generateRefreshToken(targetTime);
        tokenInfo.setRefreshToken(tokenDto.getGeneratedToken());
        tokenInfo.setRefreshTokenExpireTime(tokenDto.getExpireTime());
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

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshTokenResponse response = new RefreshTokenResponse();

        // verify refresh token
        String errorCode = jwtService.verifyJWTRefreshToken(request.getRefreshToken());
        if(StringUtils.isNotEmpty(errorCode)) {
            response.setErrorCode(errorCode);
            return response;
        }

        // verify access token
        errorCode = jwtService.verifyJWTAccessToken(request.getAccessToken());
        if(StringUtils.isNotEmpty(errorCode) && !ErrorConstant.ERR_TOKEN_001.equals(errorCode)) {
            response.setErrorCode(errorCode);
            return response;
        }

        // refresh token
        long targetTime = new Date().getTime();
        JWTPayloadDto payloadDto;
        try {
            payloadDto = jwtService.getPayloadFromJWT(request.getAccessToken());
        } catch (Exception ex) {
            response.setErrorCode(ErrorConstant.ERR_COMMON_001);
            return response;
        }

        // access token
        GeneratedTokenDto newToken = jwtService.generateAccessToken(payloadDto, targetTime, request.getIdToken());
        response.setAccessToken(newToken.getGeneratedToken());
        response.setAccessTokenExpireTime(newToken.getExpireTime());

        // refresh token
        newToken = jwtService.generateRefreshToken(targetTime);
        response.setRefreshToken(newToken.getGeneratedToken());
        response.setRefreshTokenExpireTime(newToken.getExpireTime());
        return response;
    }
}
