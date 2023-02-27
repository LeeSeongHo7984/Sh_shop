package kr.inhatc.spring.item.dto;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSearchDto {

    // 날짜 형태
    private String searchDateType;

    // 판매, 품절
    private ItemSellStatus searchSellStatus;

    // 어떤 방법으로 찾을건지
    private String searchBy;

    // 검색, 키워드
    private String searchQuery = "";

}