package demo.aws.backend.order.domain.model;

public enum OrderStatus {
    NEW,
    CREATING,
    CREATED,
    SHIPPING,
    SHIPPED,
    PAID,
    COMPLETED,
    CANCELLED;
}
