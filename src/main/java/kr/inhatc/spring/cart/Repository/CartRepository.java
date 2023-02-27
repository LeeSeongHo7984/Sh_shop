package kr.inhatc.spring.cart.Repository;

import kr.inhatc.spring.cart.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
