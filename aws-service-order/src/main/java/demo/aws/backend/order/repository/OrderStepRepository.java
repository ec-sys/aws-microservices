package demo.aws.backend.order.repository;

import demo.aws.backend.order.domain.entity.OrderStepResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStepRepository extends JpaRepository<OrderStepResult, Long> {
}
