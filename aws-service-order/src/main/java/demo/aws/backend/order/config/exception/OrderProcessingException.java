package demo.aws.backend.order.config.exception;

public class OrderProcessingException extends RuntimeException {
    public OrderProcessingException(String message, String e) {
        super(message);
    }
}
