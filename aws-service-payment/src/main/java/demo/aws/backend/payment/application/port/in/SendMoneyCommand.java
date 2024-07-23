package demo.aws.backend.payment.application.port.in;

import demo.aws.backend.payment.application.domain.model.Account.AccountId;
import demo.aws.backend.payment.application.domain.model.Money;
import jakarta.validation.constraints.NotNull;

import static demo.aws.backend.payment.common.validation.Validation.validate;

public record SendMoneyCommand(
        @NotNull AccountId sourceAccountId,
        @NotNull AccountId targetAccountId,
        @NotNull @PositiveMoney Money money
) {

    public SendMoneyCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        validate(this);
    }

}