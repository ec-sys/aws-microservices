package demo.aws.batch.order_outbox.repository;

import demo.aws.backend.order.domain.entity.OutBoxOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutBoxOrderRepository extends JpaRepository<OutBoxOrder, Long> {
    public OutBoxOrder findFirstByOrderId(long orderId);
}
