package kr.inhatc.spring.item.dto;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;			// 상품 코드

    @NotBlank(message = "상품명은 필수 항목 입니다.") // 문자열 처리
    private String itemNm;		// 상품 이름

    @NotNull(message = "가격은 필수 항목 입니다.")
    private int price;			// 상품 가격

    @NotNull(message = "재고는 필수 항목 입니다.")
    private int stockNumber;	// 재고 수량

    @NotBlank(message = "상품 설명은 필수 항목 입니다.")
    private String itemDetail;	// 상품 상세 설명

    private ItemSellStatus itemSellStatus;

    private List<Long> itemImgIds = new ArrayList<>(); // 아이디 정보 받아오려고 함

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); // 아이템 이미지

    private static ModelMapper modelMapper = new ModelMapper();

    // Dto 에서 Entity 로
    public Item createItem() {

        return modelMapper.map(this, Item.class); // 현재(this)있는 나를 Item 이라는 타입으로 만들어서 return
    }

    // Entity 에서 Dto 로
    public static ItemFormDto of(Item item) {

        return modelMapper.map(item, ItemFormDto.class);
    }


}
