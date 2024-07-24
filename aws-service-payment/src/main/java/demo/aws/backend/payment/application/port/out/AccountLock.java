package demo.aws.backend.payment.application.port.out;

import demo.aws.backend.payment.application.domain.model.Account.AccountId;

public interface AccountLock {

    void lockAccount(AccountId accountId);

    void releaseAccount(AccountId accountId);

}