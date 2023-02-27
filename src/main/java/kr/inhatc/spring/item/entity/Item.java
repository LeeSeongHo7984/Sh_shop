package kr.inhatc.spring.item.entity;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.dto.ItemFormDto;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

// 테이블로 만들어짐
@Entity
// ↓ 테이블 이름 설정
@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor // 빈생성자 만듬
@AllArgsConstructor
public class Item extends BaseEntity {

    @Id // db 테이블의 기본키에 사용할 속성을 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 값을 만들어냄
    @Column(name = "item_id") // 컬럼의 이름을 바꿀 수 있음
    private Long id;  // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm;  // 상품 이름

    @Column(nullable = false)
    private int price;  // 상품 가격

    @Column( name = "number", nullable = false)
    private int stockNumber; // 재고 수량

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Lob // 가변길의를 갖는 큰 데이터를 저장하는데 사용하는 데이터형이다
    @Column(nullable = false)
    private String itemDetail;  // 상품 상세 설명

    // 상품 수정
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber(); // 재고 수량
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

//    BaseEntity 를 상속받아서 밑에 코드를 안써도 적용됨
//    @CreationTimestamp
//    private LocalDateTime regTime; // 등록 시간
//
//    @UpdateTimestamp
//    private LocalDateTime updateTime; // 수정 시간

}
