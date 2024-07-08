package demo.aws.backend.product.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        DbType dbType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? DbType.SLAVE : DbType.MASTER;
        return dbType;
    }
}