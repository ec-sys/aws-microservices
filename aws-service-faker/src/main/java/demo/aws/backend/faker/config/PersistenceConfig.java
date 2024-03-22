package demo.aws.backend.faker.config;

import demo.aws.core.framework.auditing.ApplicationAuditAware;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef="auditorProvider", dateTimeProviderRef="auditorDateTimeProvider")
@EntityScan(basePackages = "demo.aws.backend.uaa.domain.entity")
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