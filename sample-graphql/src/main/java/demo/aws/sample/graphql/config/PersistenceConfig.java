package demo.aws.sample.graphql.config;

import demo.aws.core.framework.auditing.ApplicationAuditAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef="auditorProvider", dateTimeProviderRef="auditorDateTimeProvider")
public class PersistenceConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new ApplicationAuditAware();
    }

    @Bean
    DateTimeProvider auditorDateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }
}