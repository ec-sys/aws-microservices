package demo.aws.backend.product_cache.config.db;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfiguration {

    @Qualifier("batchDataSource")
    @Autowired
    DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
