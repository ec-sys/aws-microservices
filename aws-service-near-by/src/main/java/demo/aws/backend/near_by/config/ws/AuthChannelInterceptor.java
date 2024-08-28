package demo.aws.backend.near_by.config.ws;

import demo.aws.backend.near_by.service.TokenService;
import demo.aws.core.common_util.constant.URLConstant;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import demo.aws.core.common_util.model.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        String sessionId = accessor.getSessionId();
        log.info("sessionId AuthChannelInterceptor : {}-{}", sessionId, accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String idToken = accessor.getFirstNativeHeader(URLConstant.HEADER_ID_TOKEN);
            String accessToken = accessor.getFirstNativeHeader(URLConstant.HEADER_ACCESS_TOKEN);
            boolean isErrorToken = false;
            try {
                JWTPayloadDto payload = tokenService.getPayloadFromToken(idToken, accessToken);
                AuthInfo authObj = new AuthInfo();
                authObj.setLoginId(payload.getLoginId());
                authObj.setRoleName(payload.getRoleNames());
                authObj.setUserId(payload.getUserId());

                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                authObj.getRoleName().forEach(roleName -> authorityList.add(new SimpleGrantedAuthority(roleName)));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authObj, null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                accessor.setUser(authToken);
            } catch (Exception ex) {
                log.error("Exception parse auth : {}", ExceptionUtils.getStackTrace(ex));
                isErrorToken = true;
            }

            if (isErrorToken) {
                log.error("Invalid Token : {},{}", idToken, accessToken);
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
}
