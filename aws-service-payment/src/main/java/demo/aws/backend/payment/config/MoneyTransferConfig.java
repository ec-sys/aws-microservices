package demo.aws.backend.payment.config;

import demo.aws.backend.payment.application.domain.model.Money;
import demo.aws.backend.payment.application.domain.service.MoneyTransferProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoneyTransferConfig {
    @Value("${buckpal.transferThreshold}")
    private long transferThreshold;
    /**
     * Adds a use-case-specific {@link MoneyTransferProperties} object to the application context. The properties
     * are read from the Spring-Boot-specific buckpal.transferThreshold object.
     */
    @Bean
    public MoneyTransferProperties moneyTransferProperties(){
        return new MoneyTransferProperties(Money.of(transferThreshold));
    }
}
