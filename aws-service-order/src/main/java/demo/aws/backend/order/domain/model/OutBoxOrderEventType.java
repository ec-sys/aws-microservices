package demo.aws.backend.order.domain.model;

public enum OutBoxOrderEventType {
    CREATE_ORDER,
    CANCEL_ORDER,
    REJECT_ORDER,
    UPDATE_SHIP_ADDRESS
}
