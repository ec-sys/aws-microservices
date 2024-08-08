package demo.aws.backend.product_cache.job.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductCacheUpdateItem {
    private List<Long> productIds;
}