package kr.inhatc.spring.item.service;

import kr.inhatc.spring.item.dto.ItemFormDto;
import kr.inhatc.spring.item.dto.ItemImgDto;
import kr.inhatc.spring.item.dto.ItemSearchDto;
import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.item.entity.ItemImg;
import kr.inhatc.spring.item.repository.ItemImgRepository;
import kr.inhatc.spring.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // 이미지 등록하다 깨지면 롤백시킬려고 씀
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    // 상품 등록
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {
            Item item = itemFormDto.createItem(); // dto 를 entity 로 바꿈
            itemRepository.save(item);

            // itemImgFileList.size() : size 를 걸어야 몇 개 들어왔는지 확인 가능
            for (int i = 0; i < itemImgFileList.size(); i++) {
                ItemImg itemImg = new ItemImg();
                itemImg.setItem(item);
                
                if(i == 0) {
                    itemImg.setRepImgYn("Y");  // setRepImgYn : 대표 이미지로 쓸거냐 말거냐
                } else {
                    itemImg.setRepImgYn("N");
                }

                itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); // 서비스를 한 번 타고 넣음
            }

            return item.getId();
    }

    // 상품 수정
    public ItemFormDto getItemDetail(Long itemId) {

        // itemRepository 를 해야 db 를 당겨옴
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // entity 를 dto 로 넘기는데 매핑할 리스트를 만듬
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
                // entity 를 dto 로 변환, static 으로 된 of 라는 메서드를 씀
                ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
                itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        // 아이템 정보 들어감
        ItemFormDto itemFormDto = ItemFormDto.of(item);

        // 이미지 정보 들어감
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    // 상품 수정
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {

        // optional 때문에  .orElseThrow(EntityNotFoundException::new); 써줘야함
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        for (int i = 0; i < itemImgFileList.size(); i++) {
            // 어떤 번호로 어떤 파일을 고칠지
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }

    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }


}
