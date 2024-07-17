package demo.aws.backend.product_search.repository.impl;

import demo.aws.backend.product.domain.entity.Product;
import demo.aws.backend.product_search.graphql.filter.ProductFilter;
import demo.aws.backend.product_search.repository.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @Autowired
    EntityManager em;
    @Override
    public List<Long> findProductIdsByGraphqlFilter(ProductFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Product> product = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(product.get("categoryId"), Integer.valueOf(filter.getPrice().getValue())));

        if(Objects.nonNull(filter.getPrice())) {
            predicates.add(cb.ge(product.get("price"), Integer.valueOf(filter.getPrice().getValue())));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        // cq.multiselect(product.get("id"), product.get("price"));
        cq.select(product.get("id") );

        return em.createQuery(cq).getResultList();
    }
}
