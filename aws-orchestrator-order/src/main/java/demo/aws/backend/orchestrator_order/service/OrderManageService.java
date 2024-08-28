package demo.aws.backend.orchestrator_order.service;

import demo.aws.backend.orchestrator_order.repository.OrderRepository;
import demo.aws.backend.order.domain.constant.OrderProcessConstant;
import demo.aws.backend.order.domain.constant.OrderProcessStep;
import demo.aws.backend.order.domain.dto.OrderProcessRequestDto;
import demo.aws.backend.order.domain.dto.OrderProcessResponseDto;
import demo.aws.backend.order.domain.entity.Order;
import demo.aws.backend.order.domain.model.OrderErrorCode;
import demo.aws.backend.order.domain.model.OrderStatus;
import demo.aws.core.common_util.utils.AwsUtil;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.QueueAttributes;
import io.awspring.cloud.sqs.listener.Visibility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
public class OrderManageService {
    @Autowired
    private KafkaTemplate<String, OrderProcessRequestDto> kafkaTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @SqsListener(value = OrderProcessConstant.QUEUE_ORDER_CREATE)
    public void listenToCreateOrder(MessageHeaders headers, Visibility visibility, QueueAttributes queueAttributes, Message originalMessage) {
        String body = originalMessage.body();
        if(StringUtils.isNotBlank(body)) {
            OrderProcessRequestDto requestDto = AwsUtil.getObjectFromSqsMessage(body, OrderProcessRequestDto.class);
            kafkaTemplate.send(OrderProcessConstant.TOPIC_ORDER_CREATE, String.valueOf(requestDto.getOrderId()), requestDto);
        }
    }

    @Bean
    public KStream<String, OrderProcessResponseDto> stream(StreamsBuilder builder) {
        JsonSerde<OrderProcessResponseDto> orderSerde = new JsonSerde<>(OrderProcessResponseDto.class);
        KStream<String, OrderProcessResponseDto> stream = builder
                .stream(OrderProcessConstant.TOPIC_ORDER_CUSTOMER, Consumed.with(Serdes.String(), orderSerde));

        stream.join(
                        builder.stream(OrderProcessConstant.TOPIC_ORDER_INVENTORY),
                        this::confirm,
                        JoinWindows.of(Duration.ofSeconds(10)),
                        StreamJoined.with(Serdes.String(), orderSerde, orderSerde))
                .peek((k, o) -> log.info("Output: {}", o))
                .to("orders");

        return stream;
    }

    @Bean
    public KTable<String, OrderProcessRequestDto> table(StreamsBuilder builder) {
        KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore(OrderProcessConstant.TOPIC_ORDER_CREATE);
        JsonSerde<OrderProcessRequestDto> orderSerde = new JsonSerde<>(OrderProcessRequestDto.class);
        KStream<String, OrderProcessRequestDto> stream = builder
                .stream(OrderProcessConstant.TOPIC_ORDER_CREATE, Consumed.with(Serdes.String(), orderSerde));
        return stream.toTable(Materialized.<String, OrderProcessRequestDto>as(store)
                .withKeySerde(Serdes.String())
                .withValueSerde(orderSerde));
    }

    public OrderProcessResponseDto confirm(OrderProcessResponseDto resultInventory, OrderProcessResponseDto resultCustomer) {
        log.info("{}", resultInventory);
        log.info("{}", resultCustomer);
        Long orderId = resultInventory.getOrderId();
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        OrderProcessResponseDto resultSummarize = new OrderProcessResponseDto();
        BeanUtils.copyProperties(resultInventory, resultSummarize);
        resultSummarize.setProcessStep(OrderProcessStep.SUMMARIZE);

        OrderStatus orderStatus = OrderStatus.CREATING;
        if(orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if(OrderErrorCode.NONE.equals(resultInventory.getErrorCode()) && OrderErrorCode.NONE.equals(resultCustomer.getErrorCode())) {
                orderStatus = OrderStatus.CREATED;
            } else {
                orderStatus = OrderStatus.REJECTED;
            }
            order.setStatus(orderStatus);
            orderRepository.save(order);
        } else {
            log.error("order with id {} is not exist", orderId);
        }

        resultSummarize.setOrderStatus(orderStatus);
        return resultSummarize;
    }

}
