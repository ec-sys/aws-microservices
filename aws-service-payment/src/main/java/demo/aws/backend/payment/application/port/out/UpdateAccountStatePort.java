package demo.aws.backend.payment.application.port.out;

import demo.aws.backend.payment.application.domain.model.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}