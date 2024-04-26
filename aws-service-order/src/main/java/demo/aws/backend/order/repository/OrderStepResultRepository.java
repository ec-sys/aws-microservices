package demo.aws.backend.order.repository;

import demo.aws.backend.order.domain.entity.OrderStepResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderStepResultRepository extends MongoRepository<OrderStepResult, String> {
}
