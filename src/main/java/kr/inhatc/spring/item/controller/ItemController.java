package kr.inhatc.spring.item.controller;

import kr.inhatc.spring.item.dto.ItemFormDto;
import kr.inhatc.spring.item.dto.ItemSearchDto;
import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 상품등록
    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        ItemFormDto itemFormDto = new ItemFormDto();
        model.addAttribute("itemFormDto", itemFormDto);

        return "/item/itemForm";
    }

    @PostMapping("/admin/item/new")
    //@RequestParam("itemImgFile") : itemImgFile 은 file 등록 시 html 에 있는 name 값이랑 같아야 함
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                                @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList, Model model) {

        if(bindingResult.hasErrors()) {

            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입니다.");

            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "상품 등록중에 오류가 발생했습니다..");

            return "item/itemForm";
        }

        return "redirect:/";
    }
    
    // 상품 수정
    @GetMapping("/admin/item/{itemId}")
    // @PathVariable("itemId") : ItemId는 웹에서 넘어올 때 저걸로 받음
    public String itemDetail(@PathVariable("itemId")Long itemId, Model model) {

        try {
            ItemFormDto itemFormDto = itemService.getItemDetail(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());

            return "item/itemForm";
        }

        return "item/itemForm";
    }

    @PostMapping("/admin/item/{itemId}")
    // @Valid 는 유효성 검사, BindingResult 도 같이 써야함
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult
                                , @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList, Model model) {

        if(bindingResult.hasErrors()) {
            
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");

            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch(IOException e) {
            model.addAttribute("errorMessage", "상품 수정중에 오류가 발생했습니다.");

            return "item/itemForm";
        }

        return "redirect:/";
    }

    // 상품 조회
    @GetMapping({"/admin/items", "/admin/items/{page}"})
    // @PathVariable : 경로상에 있는 애를 받을 때 씀
    public <page> String itemList(ItemSearchDto itemSearchDto, Model model,
                            @PathVariable("page") Optional<Integer> page) {

        // 페이지 내용이 있으면(page.isPresent()) 페이지 번호(page.get)을 불러옴, 아니면 0을 가져옴
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); // 페이지당 보일 개수 (시스템은 0번부터 시작)
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 10); // 페이지 개수
        
        return "item/itemList";
    }

}
