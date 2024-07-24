package demo.aws.backend.payment.application.port.in;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyCommand command);

}