package demo.aws.backend.near_by.service;

import demo.aws.backend.near_by.config.RegisterBeansConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChannelTopicService {
    @Autowired
    RegisterBeansConfig beansConfig;

    @Lazy
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChannelTopic getChannelTopic(long userId) {
        String beanName = "topicNearBy" + userId;
        ChannelTopic bean = beansConfig.getBean(beanName, ChannelTopic.class);
        if(Objects.nonNull(bean)) {
            return bean;
        } else {
            bean = new ChannelTopic("topic-near-by-" + userId);
            beansConfig.registerBean(beanName, bean);
        }
        return bean;
    }

    public MessageSubscriber getMessageSubscriber(long userId, String loginId) {
        String beanName = "subscriberNearBy" + userId;
        MessageSubscriber bean = beansConfig.getBean(beanName, MessageSubscriber.class);
        if(Objects.nonNull(bean)) {
            return bean;
        } else {
            bean = new MessageSubscriber(userId, loginId, messagingTemplate);
            beansConfig.registerBean(beanName, bean);
        }
        return bean;
    }
}
