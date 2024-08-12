package demo.aws.backend.product_cache.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfiguration {

    @Qualifier("batchDataSource")
    @Autowired
    DataSource batchDataSource;

    @Override
    public DataSource getDataSource() {
        return batchDataSource;
    }

    @Autowired
    Properties hibernateProperties;

    @Override
    public PlatformTransactionManager getTransactionManager() {
        DefaultPersistenceUnitManager persistenceUnitManager = new DefaultPersistenceUnitManager();
        persistenceUnitManager.setDefaultDataSource(batchDataSource);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties);
        factoryBean.afterPropertiesSet();
        EntityManagerFactory batchEntityManagerFactory = factoryBean.getNativeEntityManagerFactory();

        return new JpaTransactionManager(batchEntityManagerFactory);
    }
}
