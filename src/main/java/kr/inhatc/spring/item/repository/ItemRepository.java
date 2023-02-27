package kr.inhatc.spring.item.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.inhatc.spring.item.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long>,
            QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    // QuerydslPredicateExecutor< > 꺾새안에는 바라볼 db를 넣어야함(Entity 넣으면됨)

    // find + (엔티티이름) + By 변수이름
    List<Item> findByItemNm(String ItemNm);

    // ↓ 쿼리메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // ↓ JPQL 쿼리
    // Item 은 객체고 뒤에 i는 변수명 처럼 쓰는 애임
    // @Query("select * from Item i") * 대신 @Query("select i from Item i") i(변수명)를 쓴다
    // %:itemDetail% %뒤에 : 붙여야 변수로 인식함
    @Query("select i from Item i where i.itemDetail like %:itemDetail% "
            + "order by i.price asc")
    // @Param()안에는 %:itemDetail% itemDetail 이 들어옴
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // native 쿼리
    // from 뒤에 오는 item 은 db 테이블명 그대로 적어야함(대소문자 구분)
    // where 뒤에 i.item_detail 도 테이블명 그대로 적어야함
    // nativeQuery = ture 는 nativeQuery 를 쓴건지 확인
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% "
            + "order by i.price asc", nativeQuery = true)
        // @Param()안에는 %:itemDetail% itemDetail 이 들어옴
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);




}
