package demo.aws.backend.payment.adapter.out.persistence;

import demo.aws.backend.payment.application.domain.model.Account;
import demo.aws.backend.payment.application.domain.model.Account.AccountId;
import demo.aws.backend.payment.application.domain.model.Activity;
import demo.aws.backend.payment.application.port.out.LoadAccountPort;
import demo.aws.backend.payment.application.port.out.UpdateAccountStatePort;
import demo.aws.backend.payment.common.PersistenceAdapter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
class AccountPersistenceAdapter implements
        LoadAccountPort,
        UpdateAccountStatePort {

    private final AccountJpaRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(
            AccountId accountId,
            LocalDateTime baselineDate) {

        AccountJpaEntity account =
                accountRepository.findById(accountId.getValue())
                        .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities =
                activityRepository.findByOwnerSince(
                        accountId.getValue(),
                        baselineDate);

        Long withdrawalBalance = activityRepository
                .getWithdrawalBalanceUntil(
                        accountId.getValue(),
                        baselineDate)
                .orElse(0L);

        Long depositBalance = activityRepository
                .getDepositBalanceUntil(
                        accountId.getValue(),
                        baselineDate)
                .orElse(0L);

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance);

    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

}