package demo.aws.backend.product_cache.job.processor;

import demo.aws.backend.product_cache.job.model.ProductCacheUpdateItem;
import demo.aws.backend.product_cache.service.ProductDataService;
import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class ProductCacheUpdateProcessor implements ItemProcessor<ProductCacheUpdateItem, List<ProductGraphql>> {
    private final ProductDataService productDataService;

    public ProductCacheUpdateProcessor(ProductDataService productDataService) {
        this.productDataService = productDataService;
    }

    @Override
    public List<ProductGraphql> process(ProductCacheUpdateItem item) throws Exception {
        return productDataService.getProductGraphqlByIds(item.getProductIds());
    }
}
