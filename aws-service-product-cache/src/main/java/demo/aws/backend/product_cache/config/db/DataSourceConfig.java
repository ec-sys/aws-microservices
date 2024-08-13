package demo.aws.backend.product_cache.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value("${db.for-batch.url}")
    private String urlBatch;
    @Value("${db.for-batch.username}")
    private String usernameBatch;
    @Value("${db.for-batch.password}")
    private String passwordBatch;

    @Value("${db.for-app.url}")
    private String urlApp;
    @Value("${db.for-app.username}")
    private String usernameApp;
    @Value("${db.for-app.password}")
    private String passworApp;

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.hbm2ddl.auto", "update");
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        return hibernateProp;
    }

    @Bean(name = "batchDataSource")
    public DataSource batchDataSource() {
        return createDataSource(DbType.FOR_BATCH);
    }

    @Bean(name = "appDataSource")
    public DataSource appDataSource() {
        return createDataSource(DbType.FOR_APP);
    }

    private DataSource createDataSource(DbType dbType) {
        try {
            HikariConfig hikariConfig = getHikariConfig(dbType);

            hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
            hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

            return new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            return null;
        }
    }

    private HikariConfig getHikariConfig(DbType dbType) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);

        if (DbType.FOR_BATCH.equals(dbType)) {
            hikariConfig.setJdbcUrl(urlBatch);
            hikariConfig.setUsername(usernameBatch);
            hikariConfig.setPassword(passwordBatch);
        } else {
            hikariConfig.setJdbcUrl(urlApp);
            hikariConfig.setUsername(usernameApp);
            hikariConfig.setPassword(passworApp);
        }

        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("springHikariCP");
        return hikariConfig;
    }
}
