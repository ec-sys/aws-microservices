package demo.aws.backend.order.repository;

import demo.aws.backend.order.domain.entity.OrderRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRecipientRepository extends JpaRepository<OrderRecipient, Long> {
}
