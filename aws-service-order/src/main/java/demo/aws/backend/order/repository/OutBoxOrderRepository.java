package demo.aws.backend.order.repository;

import demo.aws.backend.order.domain.entity.OutBoxOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutBoxOrderRepository extends JpaRepository<OutBoxOrder, Long> {
    public OutBoxOrder findFirstByOrderId(long orderId);
}
