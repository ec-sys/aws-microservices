package demo.aws.backend.near_by.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    ChannelTopicService channelTopicService;

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

    @Override
    public void publish(long userId, String message) {
        ChannelTopic topic = channelTopicService.getChannelTopic(userId);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}