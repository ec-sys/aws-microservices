package demo.aws.backend.orchestrator_order.service;

import demo.aws.backend.order.domain.constant.OrderProcessConstant;
import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.core.common_util.utils.AwsUtil;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.QueueAttributes;
import io.awspring.cloud.sqs.listener.Visibility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

@Service
public class OrderManageService {
    @Autowired
    private KafkaTemplate<String, OrderProcessRequestDto> kafkaTemplate;

    @SqsListener(value = OrderProcessConstant.QUEUE_ORDER_CREATE)
    public void listenToCreateOrder(MessageHeaders headers, Visibility visibility, QueueAttributes queueAttributes, Message originalMessage) {
        String body = originalMessage.body();
        if(StringUtils.isNotBlank(body)) {
            OrderProcessRequestDto requestDto = AwsUtil.getObjectFromSqsMessage(body, OrderProcessRequestDto.class);
            kafkaTemplate.send(OrderProcessConstant.TOPIC_ORDER_CREATE, requestDto.getOrderId(), requestDto);
        }
    }
}
