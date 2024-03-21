package demo.aws.backend.api_gateway.config.filter;

import demo.aws.backend.api_gateway.service.TokenService;
import demo.aws.core.common_util.constant.CommonConstant;
import demo.aws.core.common_util.constant.URLConstant;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import demo.aws.core.common_util.utils.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    TokenService tokenService;

    @Value(value = "${api.non-auth}")
    List<String> nonAuthAPIs;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            log.info("container ip: {}", InetAddress.getLocalHost().getHostName());
        } catch (Exception ex) {
            log.info("exception : {}", ex.getMessage());
        }
        ServerHttpRequest request = exchange.getRequest();

        // skip check auth with some specific uri
        if (isSkipCheckAuth(request)) return chain.filter(exchange);

        String jwtToken = getJwtFromRequest(request);
        String tokenId = getTokenIdFromRequest(request);

        try {
            JWTPayloadDto payload = tokenService.getPayloadFromToken(tokenId, jwtToken);
            ServerWebExchange modifiedExchange = exchange.mutate()
                    // modify the original request:
                    .request(originalRequest -> {
                        originalRequest.header(CommonConstant.HEADER_AUTH_INFO, CommonUtil.createAuthInfoStr(payload)).build();
                    })
                    .build();
            return chain.filter(modifiedExchange);
        } catch (Exception ex) {
            log.error("{}", ExceptionUtils.getStackTrace(ex));
            return this.onError(exchange, "ERROR_001", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isSkipCheckAuth(ServerHttpRequest request) {
        String urlPath = request.getPath().value();
        if (URLConstant.LOGIN_URI.equals(urlPath) || URLConstant.REFRESH_TOKEN.equals(urlPath)) {
            return true;
        }
        if (URLConstant.WS_UPGRADE.equalsIgnoreCase(request.getHeaders().getUpgrade())) {
            return true;
        }

        if (urlPath.startsWith("/api/public")) return true;

        if (nonAuthAPIs.contains(urlPath)) return true;

        return false;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorCode, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("errorCode", errorCode);
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getJwtFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(URLConstant.HEADER_AUTHORIZATION);

        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return StringUtils.EMPTY;
    }

    private String getTokenIdFromRequest(ServerHttpRequest request) {
        return request.getHeaders().getFirst(URLConstant.HEADER_ID_TOKEN);
    }
}
