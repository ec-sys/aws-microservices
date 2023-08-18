package demo.aws.core.framework.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import demo.aws.core.framework.constant.ErrorConstant;
import demo.aws.core.framework.constant.JwtAlgorithm;
import demo.aws.core.framework.dto.GeneratedTokenDto;
import demo.aws.core.framework.dto.JWTPayloadDto;
import demo.aws.core.framework.security.model.LoginInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.algorithm-name}")
    private JwtAlgorithm algorithmName;

    @Value("${jwt.access-token-expire-in-second}")
    private long accessTokenExpireInSecond;

    @Value("${jwt.refresh-token-expire-in-second}")
    private long refreshTokenExpireInSecond;

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm algorithm;

    @PostConstruct
    private void initializeBean() {
        setAlgorithm();
    }

    private void setAlgorithm() {
        switch (this.algorithmName) {
            case HS256:
                this.algorithm = Algorithm.HMAC256(this.secret);
                break;
            case HS384:
                this.algorithm = Algorithm.HMAC384(this.secret);
                break;
            default:
                this.algorithm = Algorithm.HMAC512(this.secret);
                break;
        }
    }

    public GeneratedTokenDto generatedTokenDto(LoginInfo loginInfo, long targetTime) {
        GeneratedTokenDto tokenDto = new GeneratedTokenDto();
        long expireTime = targetTime + (accessTokenExpireInSecond * 1000);
        tokenDto.setExpireTime(expireTime);
        Date expireDate = new Date(expireTime);

        String generatedToken = JWT.create()
                .withIssuer(issuer)
                .withClaim("userId", loginInfo.getUserId())
                .withClaim("roleNames", loginInfo.getRoleName())
                .withExpiresAt(expireDate)
                .sign(algorithm);
        tokenDto.setGeneratedToken(generatedToken);
        return tokenDto;
    }

    public GeneratedTokenDto generateAccessToken(JWTPayloadDto payloadDto, long targetTime, String tokenId) {
        GeneratedTokenDto tokenDto = new GeneratedTokenDto();
        long expireTime = targetTime + (accessTokenExpireInSecond * 1000);
        tokenDto.setExpireTime(expireTime);
        Date expireDate = new Date(expireTime);

        String generatedToken = JWT.create()
                .withJWTId(tokenId)
                .withIssuer(issuer)
                .withClaim("userId", payloadDto.getUserId())
                .withClaim("roleNames", payloadDto.getRoleNames())
                .withExpiresAt(expireDate)
                .sign(algorithm);
        tokenDto.setGeneratedToken(generatedToken);
        return tokenDto;
    }

    public GeneratedTokenDto generateRefreshToken(long targetTime) {
        GeneratedTokenDto tokenDto = new GeneratedTokenDto();
        long expireTime = targetTime + (refreshTokenExpireInSecond * 1000);
        tokenDto.setExpireTime(expireTime);
        Date expireDate = new Date(expireTime);

        String refreshToken = JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(expireDate)
                .sign(algorithm);
        tokenDto.setGeneratedToken(refreshToken);
        return tokenDto;
    }

    /**
     * verify access token
     *
     * @param token verify token value
     * @return blank if token valid, ERR_TOKEN_001 if token expired, ERR_TOKEN_002 for other invalid token
     */
    public String verifyJWTAccessToken(String token) {
        return verifyToken(token, accessTokenExpireInSecond);
    }

    /**
     * verify refresh token
     *
     * @param token verify token value
     * @return blank if token valid, ERR_TOKEN_001 if token expired, ERR_TOKEN_002 for other invalid token
     */
    public String verifyJWTRefreshToken(String token) {
        return verifyToken(token, refreshTokenExpireInSecond);
    }

    private String verifyToken(String token, long expireInSecond) {
        String errorCode = StringUtils.EMPTY;
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .acceptExpiresAt(expireInSecond)
                    .build();
            verifier.verify(token);
        } catch (TokenExpiredException ex) {
            errorCode = ErrorConstant.ERR_TOKEN_001;
        } catch (JWTVerificationException ex) {
            errorCode = ErrorConstant.ERR_TOKEN_002;
        }
        return errorCode;
    }

    public JWTPayloadDto getPayloadFromJWT(String token) throws IllegalAccessException {
        DecodedJWT decodedJWT = JWT.decode(token);
        // get field
        List<Field> fields = Arrays.stream(JWTPayloadDto.class.getDeclaredFields())
                .filter(field -> !field.getName().startsWith("$")).collect(Collectors.toList());
        fields.forEach(field -> field.setAccessible(true));
        // set field
        JWTPayloadDto jwtPayloadDto = new JWTPayloadDto();
        Claim claim = decodedJWT.getClaim("userId");
        if (claim != null) {
            jwtPayloadDto.setUserId(claim.asInt());
        }
        claim = decodedJWT.getClaim("roleNames");
        if (claim != null) {
            jwtPayloadDto.setRoleNames(claim.asList(String.class));
        }
        return jwtPayloadDto;
    }
}
