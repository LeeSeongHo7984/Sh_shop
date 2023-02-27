package kr.inhatc.spring.item.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.item.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static kr.inhatc.spring.item.entity.QItem.*;

@SpringBootTest
public class ItemRepositoryTest {

    // Entity Manager 생성
    @Autowired
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);

        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }

    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        // 리스트를 만들어서 db에 넣어놓음
        this.createItemList();
        // ↓ 리스트를 만들어서 ItemRepository 레포지토리안에 findByItemNm에 테스트상품1을 넣어서 찾음
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for(Item item : itemList){
            System.out.println(item.toString());
        }

    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        // 리스트를 만들어서 db에 넣어놓음
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }

    }

    @Test
    @DisplayName("JPQL 쿼리")
    public void findByItemDetailTest() {
        createItemList(); // 시작되자마자 리스트를 비교해서 값을 넣어줌

        List<Item> itemList = itemRepository.findByItemDetail("테스트");

        for (Item item : itemList) {
            System.out.println(item);
        }

    }

    @Test
    @DisplayName("Native 쿼리")
    public void findByItemDetailNativeTest() {
        createItemList(); // 시작되자마자 리스트를 비교해서 값을 넣어줌

        List<Item> itemList = itemRepository.findByItemDetailNative("테스트");

        for (Item item : itemList) {
            System.out.println(item);
        }

    }

    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest() {
        createItemList();
        
        // 쿼리를 만들수 있는 Factory 만듬
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//        QItem qItem = item; 위에 import를 해줘서 안써도됨
        //↓ 아래코드랑 같은 표현임
//        QItem qItem = new QItem("i");

            List<Item> list = queryFactory
                .selectFrom(item)
                // 여기서 itemSellStatus랑 원래 가지고 있던 itemSellStatus랑 같은 애를 당겨와서 비교
                .where(item.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(item.itemDetail.like("%" + "1" + "%"))
                // qItem에 있는 애들중에
                .orderBy(item.price.asc())
                    // 결과를 뽑아올때 .fetch()를 씀 (리스트를 땡겨올 수 있음)
                    // fetch의 결과물이 List<Item>이다
                .fetch();

        for (Item item : list) {
            System.out.println(item);
        }

    }

    public void createItemList2(){
        for(int i=1; i<=5; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("querydsl 테스트2")
    public void querydslTest2() {
        createItemList2();

        String itemDetail = "테스트";
        int price = 10003;
        String itemSellState = "SELL";

        QItem item = QItem.item;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.gt(price)); // item.price는 db값, 뒤에 price는 변수값

        if(StringUtils.equals(itemSellState, ItemSellStatus.SELL)) {
//            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        // 페이징은 springframework.data.domain껄 써야함
        // of( , ) : 괄호안에 첫번째는 시작페이지, 두번째는 한페이지당 볼 갯수
        Pageable pageable = PageRequest.of(1, 5);

        // findAll은 predicate, pageable를 씀
        Page<Item> findAll = itemRepository.findAll(builder, pageable);

        System.out.println("전체 갯수 : " + findAll.getTotalElements());

        List<Item> content = findAll.getContent();
        for (Item item2 : content) {
            System.out.println(item2);
        }

    }

    @Test
    void test() {
        Item item = new Item();
        System.out.println(item);
    }
}
