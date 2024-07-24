package demo.aws.backend.payment.application.port.out;

import demo.aws.backend.payment.application.domain.model.Account;
import demo.aws.backend.payment.application.domain.model.Account.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}