package kr.inhatc.spring.item.entity;

import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
public class ItemImg extends BaseEntity {

    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement 역활
    @Column(name = "item_img_id")
    private Long id;

    private String imgName; // 파일에 사용될 이미지 이름

    private String oriImgName; // 원래 이미지 이름

    private String imgUrl; // 이미지 주소

    private String repImgYn; // 이미지 보일 여부(Y, N)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}
