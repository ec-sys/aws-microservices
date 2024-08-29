package demo.aws.backend.near_by.api;

import demo.aws.backend.near_by.event.SendLocationEvent;
import demo.aws.backend.near_by.service.MessagePublisher;
import demo.aws.core.common_util.model.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class LocationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    MessagePublisher messagePublisher;

    @MessageMapping("/bind_location")
    public void bindLocation(SendLocationEvent event, Principal principal) {
        Controllers.setAuthentication(principal);
        // AuthInfo authInfo = (AuthInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //List<UserFriend> userFriends = userFriendRepository.findByUserId(authInfo.getUserId());
    }

//    @Autowired
//    ChannelTopic topicNearByDefault;
//
//    @Qualifier("topicNearBy1")
//    @Autowired
//    ChannelTopic topicNearBy1;
//
//    @Qualifier("topicNearBy2")
//    @Autowired
//    ChannelTopic topicNearBy2;
//
//    @Qualifier("topicNearBy3")
//    @Autowired
//    ChannelTopic topicNearBy3;
//
//    @Qualifier("topicNearBy4")
//    @Autowired
//    ChannelTopic topicNearBy4;
//    private ChannelTopic getNearByTopic(long userId) {
//        if(userId == 1) {
//            return topicNearBy1;
//        } else if(userId == 2) {
//            return topicNearBy2;
//        } else if(userId == 3) {
//            return topicNearBy3;
//        } else if(userId == 4) {
//            return topicNearBy4;
//        } else {
//            return topicNearByDefault;
//        }
//    }

    @MessageMapping("/send_location")
    public void sendLocation(SendLocationEvent event, Principal principal) {
        Controllers.setAuthentication(principal);
        AuthInfo authInfo = (AuthInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        messagePublisher.publish(authInfo.getUserId(), "location " + event.getLocation());
    }
}