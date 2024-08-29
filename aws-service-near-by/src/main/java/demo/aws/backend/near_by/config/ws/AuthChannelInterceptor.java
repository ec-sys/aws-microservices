package demo.aws.backend.near_by.config.ws;

import demo.aws.backend.near_by.domain.entity.UserFriend;
import demo.aws.backend.near_by.repository.UserFriendRepository;
import demo.aws.backend.near_by.service.ChannelTopicService;
import demo.aws.backend.near_by.service.MessageSubscriber;
import demo.aws.backend.near_by.service.TokenService;
import demo.aws.core.common_util.constant.URLConstant;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import demo.aws.core.common_util.model.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
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
    @Autowired
    ChannelTopicService channelTopicService;
    @Autowired
    RedisMessageListenerContainer container;
    @Autowired
    UserFriendRepository userFriendRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        String sessionId = accessor.getSessionId();

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("sessionId AuthChannelInterceptor : {}-{}", sessionId, accessor.getCommand());
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
                accessor.setUser(new UserWebSocket(authObj));

                List<UserFriend> userFriends = userFriendRepository.findByUserId(authObj.getUserId());
                for (UserFriend friend : userFriends) {
                    log.info("user {}-friend {}",authObj.getUserId(), friend.getFriendId());
                    MessageSubscriber subscriber = channelTopicService.getMessageSubscriber(authObj.getUserId(), authObj.getLoginId());
                    ChannelTopic topic = channelTopicService.getChannelTopic(friend.getFriendId());
                    container.addMessageListener(subscriber, topic);
                }
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
        } else if(SimpMessageType.HEARTBEAT.equals(accessor.getMessageType())){
            // log.info("MessageType {}: ", accessor.getMessageType().name());
        } else {
            log.info("MessageType {}: ", accessor.getMessageType().name());
        }
        return message;
    }
}
