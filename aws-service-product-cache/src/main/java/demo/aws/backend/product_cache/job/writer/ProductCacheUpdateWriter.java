package demo.aws.backend.product_cache.job.writer;

import demo.aws.backend.product_cache.domain.constant.CacheNameConstant;
import demo.aws.core.common_util.graphql.product.ProductGraphql;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

public class ProductCacheUpdateWriter implements ItemWriter<List<ProductGraphql>> {

    private final CacheManager cacheManager;

    public ProductCacheUpdateWriter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void write(Chunk<? extends List<ProductGraphql>> chunk) throws Exception {
        Cache cache = cacheManager.getCache(CacheNameConstant.PRODUCT_GRAPHQL);
        for (List<ProductGraphql> items : chunk.getItems()) {
            for (ProductGraphql item : items) {
                cache.put(item.getId(), item);
            }
        }
    }
}
