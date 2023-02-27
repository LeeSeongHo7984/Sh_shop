package kr.inhatc.spring.order.entity;

import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // 주문정보

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item; // 상품정보

    private int orderPrice;

    private int count;

//    BaseEntity 를 상속받아서 밑에 코드를 안써도 적용됨
//    private LocalDateTime regTime; // 등록시간
//
//    private LocalDateTime updateTime; //수정시간

}
