package demo.aws.backend.product_cache.config.db;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableCaching
@EnableScheduling
public class BatchConfig extends DefaultBatchConfiguration {

    @Qualifier("batchDataSource")
    @Autowired
    DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    Properties hibernateProperties;

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitManager(persistenceUnitManager());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties);
        return factoryBean.getNativeEntityManagerFactory();
    }

    public PersistenceUnitManager persistenceUnitManager() {
        DefaultPersistenceUnitManager persistenceUnitManager = new DefaultPersistenceUnitManager();
        persistenceUnitManager.setPackagesToScan(
                "org.springframework.batch.core"
        );
        persistenceUnitManager.setDefaultDataSource(dataSource);
        return persistenceUnitManager;
    }

//    @Primary
//    @Bean(name = "batchEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(EntityManagerFactoryBuilder builder,
//                                                                            @Qualifier("batchDataSource") DataSource batchDataSource){
//        return builder
//                .dataSource(batchDataSource)
//                .persistenceUnit("batch")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "batchTransactionManager")
//    public PlatformTransactionManager batchTransactionManager(@Qualifier("batchEntityManagerFactory") EntityManagerFactory
//                                                                    batchEntityManagerFactory) {
//        return new JpaTransactionManager(batchEntityManagerFactory);
//    }

}
