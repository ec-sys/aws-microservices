package demo.aws.core.framework.security;

import demo.aws.core.framework.dto.GeneratedAccessTokenDto;
import demo.aws.core.framework.dto.GeneratedRefreshTokenDto;
import demo.aws.core.framework.dto.GeneratedTokenDto;
import demo.aws.core.framework.dto.JWTPayloadDto;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtService {
    private String issuer;
    private long accessTokenExpireInSecond;
    private long refreshTokenExpireInSecond;
    private int keySize;

    public JwtService(String issuer, long accessTokenExpireInSecond, long refreshTokenExpireInSecond) {
        this.issuer = issuer;
        this.accessTokenExpireInSecond = accessTokenExpireInSecond;
        this.refreshTokenExpireInSecond = refreshTokenExpireInSecond;
        this.keySize = 2048;
    }
    public JwtService(String issuer, long accessTokenExpireInSecond, long refreshTokenExpireInSecond, int keySize) {
        this.issuer = issuer;
        this.accessTokenExpireInSecond = accessTokenExpireInSecond;
        this.refreshTokenExpireInSecond = refreshTokenExpireInSecond;
        this.keySize = keySize;
    }

    /**
     * generated access token by asymmetric key algorithms
     * @param payloadDto
     * @param targetTime
     * @param tokenId
     * @return
     * @throws NoSuchAlgorithmException
     */
    public GeneratedAccessTokenDto generateAccessToken(JWTPayloadDto payloadDto, long targetTime, String tokenId) throws Exception {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(this.keySize);

        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        GeneratedAccessTokenDto tokenDto = new GeneratedAccessTokenDto();
        long expireTime = targetTime + (accessTokenExpireInSecond * 1000);
        tokenDto.setExpireTime(expireTime);
        Date expireDate = new Date(expireTime);

        String generatedToken = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(payloadDto.getLoginId())
                .setExpiration(expireDate)
                .claim("userId", payloadDto.getUserId())
                .claim("roleNames", payloadDto.getRoleNames())
                .signWith(privateKey, SignatureAlgorithm.RS256).compact();
        tokenDto.setGeneratedToken(generatedToken);
        tokenDto.setGeneratedPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        tokenDto.setGeneratedPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        return tokenDto;
    }

    /**
     * generated refresh token by asymmetric key algorithms
     * @param loginId
     * @param targetTime
     * @param privKeyStr
     * @return
     * @throws Exception
     */
    public GeneratedRefreshTokenDto generateRefreshToken(String loginId, long targetTime, String privKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKeyFromString(privKeyStr);
        GeneratedRefreshTokenDto tokenDto = new GeneratedRefreshTokenDto();
        long expireTime = targetTime + (refreshTokenExpireInSecond * 1000);
        tokenDto.setExpireTime(expireTime);
        Date expireDate = new Date(expireTime);

        String refreshToken = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(loginId)
                .setExpiration(expireDate)
                .signWith(privateKey, SignatureAlgorithm.RS256).compact();
        tokenDto.setGeneratedToken(refreshToken);
        return tokenDto;
    }

    public PrivateKey getPrivateKeyFromString(String privKeyStr) throws Exception {
        byte[] sigBytes = Base64.getDecoder().decode(privKeyStr.getBytes("UTF-8"));
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(sigBytes);
        KeyFactory keyFact = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFact.generatePrivate(privateKeySpec);
        return privateKey;
    }

    public PublicKey getPublicKeyFromString(String pubKeyStr) throws Exception {
        byte[] encodedPublicKey = Base64.getDecoder().decode(pubKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedPublicKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public JWTPayloadDto getPayloadFromJWT(String token, String pubKeyStr) throws Exception {
        PublicKey publicKey = getPublicKeyFromString(pubKeyStr);
        Jws parseClaims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
        JWTPayloadDto payloadDto = new JWTPayloadDto();
        parseClaims.getBody();
        return payloadDto;
    }
}
