package kr.inhatc.spring.order.repository;

import kr.inhatc.spring.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
