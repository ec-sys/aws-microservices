package demo.aws.backend.payment.application.domain.service;

import demo.aws.backend.payment.application.domain.model.Money;
import demo.aws.backend.payment.application.port.in.GetAccountBalanceUseCase;
import demo.aws.backend.payment.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(GetAccountBalanceQuery query) {
        return loadAccountPort.loadAccount(query.accountId(), LocalDateTime.now())
                .calculateBalance();
    }
}