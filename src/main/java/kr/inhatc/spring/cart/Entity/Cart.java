package kr.inhatc.spring.cart.Entity;

import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
public class Cart extends BaseEntity {

    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
