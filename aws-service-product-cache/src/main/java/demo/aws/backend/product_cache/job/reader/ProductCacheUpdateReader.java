package demo.aws.backend.product_cache.job.reader;

import demo.aws.backend.product_cache.job.model.ProductCacheUpdateItem;
import demo.aws.backend.product_cache.repository.ProductRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;

import java.util.ArrayList;
import java.util.List;

public class ProductCacheUpdateReader implements ItemReader<ProductCacheUpdateItem> {

    private final ProductRepository productRepository;
    private ItemReader<ProductCacheUpdateItem> delegate;

    public ProductCacheUpdateReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductCacheUpdateItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (delegate == null) {
            delegate = new IteratorItemReader<>(products());
        }
        return delegate.read();
    }

    private List<ProductCacheUpdateItem> products() {
        List<ProductCacheUpdateItem> items = new ArrayList<>();
        int totalRecord = (int) productRepository.count();
        int limit = 2000;
        int pageSize = totalRecord / limit;
        if (pageSize * limit < totalRecord) {
            pageSize++;
        }
        // pageSize = 300;
        for (int i = 0; i < pageSize; i++) {
            ProductCacheUpdateItem item = new ProductCacheUpdateItem();
            item.setProductIds(productRepository.findAllIdPagination(limit, i * limit));
            items.add(item);
        }
        return items;
    }
}
