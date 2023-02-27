package kr.inhatc.spring.item.dto;

import kr.inhatc.spring.item.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

@Getter
@Setter
@ToString
public class ItemImgDto {

    private Long id; // Id

    private String imgName; // 이미지 이름

    private String oriImgName; // 이미지 원래 이름

    private String imgUrl; // 이미지 경로

    private String repImgYn; // 대표이미지 표시 여부

    private static ModelMapper modelMapper = new ModelMapper();

    // entity 를 받으면  dto 로 변환하는 코드
    public static ItemImgDto of(ItemImg itemImg) {

        // 아이템 이미지 dto 로 받아서 매핑한다는 뜻
        return modelMapper.map(itemImg, ItemImgDto.class);
    } 


}
