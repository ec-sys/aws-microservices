package demo.aws.backend.orchestrator_order.repository;

import demo.aws.backend.order.domain.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
}
