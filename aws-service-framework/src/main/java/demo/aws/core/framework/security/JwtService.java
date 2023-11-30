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
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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

    public void testJWT() throws Exception{
        String privKeyStr = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCfIIx9a0gT7PGewUkn+euubcxzb5FTccAukMISkXffm7jdFmpvGwaGIwFu7nEcdCNrW9bLS2EVf0pFuoylTUgCLpTnFvVmeyVXZhuKsiCUdh5CCvDyL5rXresJcLfVXKHSosjS7qwkbh80c/fvicMASot9qCaZtjuG6Yv+7WptrH7N+Kbq6yH7Ordemr90KLHwLD6GnYuyxV+ATU28a5iT5eTD+rtSoCg1JQIfkzu17BvDf2ZuivZO9Cn8kx1k8B4xxOni0qrp/ca5tp/IKeuaAlZXjQQNtCTadwockKL5SVATYm9r5wtYEl2SEO5YqAQtBKCnHrQmu3iCi/nCn2E3AgMBAAECggEAAT/TyVCaY5MyvTUJlrFMeRfiNUa7MjU2WDYEbtJFth/0BHQK1M3dvtygksrGJfRZUesAId8Wewr1LyttFi7ZOQlRDONrpB8Hi6OUlrMGPpzEBqpoGC6tzp5OQAWR/mGJuY7xeIrLA/e+mUhJ9VlDJ/syxVIFCHyvF7rDEIp4zHCzdiaQoqVxsgK0X8kKzlXiHtFIS73G3NsEPHWonQ51l9/2aclsp/IFZjQpAa8dFyytbzWr/rApiHP5uiu33RWg4Y/rYe+tWbV6hFfgQTccJtrvOCJo3J8opzPfaTRdABh04PkiDogLxO2lgQW/GwfgFJSXHeNfLV6WH2KVE091yQKBgQDeNtF34aY2vnmzuyh9IWI4SPUnsD2Ee0dQe38kh/lrjsEw1d0l+PhmZw3DDrlY8LwjVpAgN/PQmN9EQMeJvMrT+NLxi0CZRzEU9v7s6Wyj9WIj/P4pU5PV9hunpgjmgA5WBoLob9476rW8xRIQSh8BKyTZ5M1cBbVKiyYD8f9fBQKBgQC3UjZeiozQkLby3H5nOIW437vMMhORGgG0zg1tFnFksT4znV3GRNfO29HodS2fMScYNYCkwb65obxmXmuJJ1iNj/94IHe/lJv6EXBBAR4KKFpGfRxY2vQBes9Fwk5Nmf5KXD4yYSK5R9cJLfdq0QDwQcx8KK5tTqWayYraz2bcCwKBgQDBpPW/SZXGCREXbhanjKsCL5nr/ypsw91upmAy3oedkmaKfyRbze3xlwaoH7GnbwoUpX+tg/TlqAKQgP8h6bK7LA/vTBF8QUzlXyTiIbx52QGwKOz36IxhHToWS5tf+uIDV2NG2Q3KGwxKt1W5ek4wf396nmfTNGo6zq3PLFkFXQKBgQCSSIAqINUM+O2EDn6vT2MliyMIZk1G1EHYTTRZgDcr6mOAPGq/899hqfduT1MAXPCdWNQ2pIPVDUKRTXbyEvuOuwFYkk/8CFIO0iuJM6MM5/ZhmSQto0pig+Ux/UMOmXtZSxI2kVxsDO+C27M7AVyGW3sY+D136/Ip+flbcXTa0QKBgQDc0VxQj3Hqj30rTI5ndrymDPZ16VjUj2UNnDUbE9mAGVcqj+zP+gghPjYERS83hQh4vCdQG12vB3+bhFA3jrxFw9NQ6aFGT0Jn4fa8dt+o2+vlVpM1n/7nUyzpxgnnT2iwPEcPVkXIsIJWqmYanPxAXveBSCx0u3tL04NPIXhIIQ==";

        byte[] sigBytes = new byte[0];
        try {
            sigBytes = Base64.getDecoder().decode(privKeyStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(sigBytes);
        KeyFactory keyFact = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = null;

        try {
            privateKey = keyFact.generatePrivate(privateKeySpec);  //throws exception
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        RSAPrivateCrtKey rsaCrtKey = (RSAPrivateCrtKey) privateKey; // May throw a ClassCastException
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaCrtKey.getModulus(), rsaCrtKey.getPublicExponent());
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);
        System.out.println(convertToPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded())));
    }
    public void testJWTWithRsa() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Public Key:");
        System.out.println(convertToPublicKey(encodedPublicKey));
        System.out.println(convertToPublicKey(Base64.getEncoder().encodeToString(privateKey.getEncoded())));
        String token = generateJwtToken(privateKey);
        System.out.println("TOKEN:");
        System.out.println(token);
        printStructure(token, kp.getPublic());
    }

    public String generateJwtToken(PrivateKey privateKey) {
        String token = Jwts.builder().setSubject("adam")
                .setExpiration(new Date(2018, 1, 1))
                .setIssuer(issuer)
                .claim("groups", new String[] { "user", "admin" })
                // RS256 with privateKey
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
        return token;
    }

    //Print structure of JWT
    public void printStructure(String token, PublicKey publicKey) {
        Jws parseClaimsJws = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);

        System.out.println("Header     : " + parseClaimsJws.getHeader());
        System.out.println("Body       : " + parseClaimsJws.getBody());
        System.out.println("Signature  : " + parseClaimsJws.getSignature());
    }


    // Add BEGIN and END comments
    private String convertToPublicKey(String key){
        StringBuilder result = new StringBuilder();
        result.append("-----BEGIN PUBLIC KEY-----\n");
        result.append(key);
        result.append("\n-----END PUBLIC KEY-----");
        return result.toString();
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
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        GeneratedTokenDto tokenDto = new GeneratedTokenDto();
        long expireTime = targetTime + (accessTokenExpireInSecond * 1000);
        tokenDto.setExpireTime(expireTime);
        Date expireDate = new Date(expireTime);

        String generatedToken = Jwts.builder()
                .setSubject(payloadDto.getLoginId())
                .setExpiration(expireDate)
                .setIssuer(issuer)
                .claim("userId", payloadDto.getUserId())
                .claim("roleNames", payloadDto.getRoleNames())
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
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
