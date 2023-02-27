package kr.inhatc.spring.order.repository;

import kr.inhatc.spring.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
