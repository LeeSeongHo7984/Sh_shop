package kr.inhatc.spring.item.service;

import kr.inhatc.spring.item.entity.ItemImg;
import kr.inhatc.spring.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.io.IOException;

@Service
@RequiredArgsConstructor // 다른 클래스나 인터페이스를 가져다 쓸 때 사용
@Transactional // 이미지 등록하다 깨지면 롤백시킬려고 씀
public class ItemImgService {

    @Value(value = "${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String oriImgName = itemImgFile.getOriginalFilename(); // 파일 기존 이름
        String imgName = ""; // 파일 이름
        String imgUrl = ""; // 파일 경로

        if(!StringUtils.isEmpty(oriImgName)) {
            // itemImgLocation 은 application.properties 에 적어놓은 경로 ( @value 어노테이션을 사용하여 가져와야함)
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        // 실제 상품 이미지 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);

    }

    // 이미지 수정
    public void updateItemImg(Long ItemImgId, MultipartFile itemImgFile) throws IOException {
        if(!itemImgFile.isEmpty()) {
            ItemImg itemImg = itemImgRepository.findById(ItemImgId).orElseThrow(EntityExistsException::new);

            // 이미지 변경시 이전 이미지 지우기
            if(!StringUtils.isEmpty(itemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
            }

            String oriName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;

            itemImg.updateItemImg(oriName, imgName, imgUrl);
        }
    }


}
