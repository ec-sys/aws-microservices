package demo.aws.backend.order.repository;

import demo.aws.backend.order.domain.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
