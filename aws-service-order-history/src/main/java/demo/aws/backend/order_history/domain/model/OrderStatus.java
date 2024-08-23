package demo.aws.backend.order_history.domain.model;

public enum OrderStatus {
    NEW,
    CREATING,
    CREATED,
    SHIPPING,
    SHIPPED,
    PAID,
    COMPLETED,
    CANCELLED,
    REJECTED,
    ROLLBACK;
}
