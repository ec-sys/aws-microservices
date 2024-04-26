package demo.aws.backend.order.domain.constant;

public class OrderProcessConstant {
    // queue
    public final static String QUEUE_ORDER_PROCESS = "order-process-queue";
    public final static String QUEUE_ORDER_CREATE = "order-create-queue";
    public final static String QUEUE_ORDER_CUSTOMER = "order-customer-queue";
    public final static String QUEUE_ORDER_INVENTORY = "order-inventory-queue";

    // kafka topic
    public final static String TOPIC_ORDER_CREATE = "create-orders";
    public final static String TOPIC_ORDER_CUSTOMER = "customer-orders";
    public final static String TOPIC_ORDER_INVENTORY = "inventory-orders";

    // step
    public final static String STEP_CUSTOMER = "order-inventory-queue";
}
