package demo.aws.backend.rtm.config.interceptor;

import demo.aws.core.common_util.constant.URLConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {

//    @Autowired
//    JwtTokenUtil jwtTokenUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        String sessionId = accessor.getSessionId();
        log.info("sessionId AuthChannelInterceptor : {}", sessionId);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader(URLConstant.HEADER_AUTHORIZATION);
            boolean isErrorToken = false;
//            String errorCode = jwtTokenUtil.verifyJWTAccessToken(jwtToken);
//            if (StringUtils.isEmpty(jwtToken) || StringUtils.isNotEmpty(errorCode)) {
//                isErrorToken = true;
//            }
//
//            try {
//                JWTPayloadDto payload = jwtTokenUtil.getPayloadFromJWT(jwtToken);
//                Authentication authentication = new UsernamePasswordAuthenticationToken(createLoginInfo(payload), null, null);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                accessor.setUser(authentication);
//            } catch (Exception ex) {
//                isErrorToken = true;
//            }

            if(isErrorToken) {
                log.error("Invalid Token : ", jwtToken);
                throw new IllegalArgumentException("Invalid Token");
            }
            log.info("sessionId AuthChannelInterceptor CONNECT : {}", sessionId);
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.info("sessionId AuthChannelInterceptor DISCONNECT : {}", sessionId);
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            log.info("sessionId AuthChannelInterceptor SUBSCRIBE : {}", sessionId);
        } else {
            log.info("MessageType {}: ", accessor.getMessageType().name());
        }
        return message;
    }

//    private LoginInfo createLoginInfo(JWTPayloadDto jwtPayload) {
//        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setUserId(jwtPayload.getUserId());
//        loginInfo.setCustomerId(jwtPayload.getCustomerId());
//        loginInfo.setSupplierId(jwtPayload.getSupplierId());
//
//        RequestInfo requestInfo = new RequestInfo();
//        loginInfo.setRequestInfo(requestInfo);
//        return loginInfo;
//    }
}
