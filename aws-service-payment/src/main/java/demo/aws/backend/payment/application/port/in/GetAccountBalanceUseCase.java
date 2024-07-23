package demo.aws.backend.payment.application.port.in;

import demo.aws.backend.payment.application.domain.model.Account.AccountId;
import demo.aws.backend.payment.application.domain.model.Money;

public interface GetAccountBalanceUseCase {

    Money getAccountBalance(GetAccountBalanceQuery query);

    record GetAccountBalanceQuery(AccountId accountId) {
    }
}
