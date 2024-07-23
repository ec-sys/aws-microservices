package demo.aws.backend.payment.adapter.out.persistence;

import demo.aws.backend.payment.application.domain.model.Account.AccountId;
import demo.aws.backend.payment.application.port.out.AccountLock;
import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(AccountId accountId) {
        // do nothing
    }

}