package kr.inhatc.spring.item.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.dto.ItemSearchDto;
import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.item.entity.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static kr.inhatc.spring.item.entity.QItem.item;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;

    // 쿼리팩토리는 EntityManager 을 이용해야 함
    public ItemRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory
                .selectFrom(item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()), // 판매, 품절
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())) // 검색, 키워드
                .orderBy(item.id.desc())
                .offset(pageable.getOffset()) // offset : 어디부터 할거냐, getOffset 은 시작한 위치를 알 수 있음
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()), 
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total); // 인터페이스는 객체를 만들 수 없어서 객체를 만들어서 리턴해야 함
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){

        return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);
    }
    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){    // 하루 전
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){    // 일주일 전
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){    // 한달 전
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){    // 6개월 전
            dateTime = dateTime.minusMonths(6);
        }

        return item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){ // 상품명 검색
            return item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){ // 작성자 검색
            return item.createBy.like("%" + searchQuery + "%");
        }

        return null;
    }

}
