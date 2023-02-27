package kr.inhatc.spring.order.entity;

import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.order.constant.OrderStatus;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // mappedBy : 양방향에서 주인 설정, orphanRemoval = true : 고아객체가 되면 날림
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    private LocalDateTime orderDate;
    
    private OrderStatus orderStatus; // 주문상태

//    BaseEntity 를 상속받아서 밑에 코드를 안써도 적용됨
//    @CreationTimestamp
//    private LocalDateTime regTime;  // 등록시간
//
//    @UpdateTimestamp
//    private LocalDateTime updateTime;   // 수정시간
    

}
