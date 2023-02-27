package kr.inhatc.spring.thymeleaf.controller;

import kr.inhatc.spring.item.dto.ItemFormDto;
import kr.inhatc.spring.utils.entity.BaseTimeEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
// requestMapping에 경로를 쓰면 쓴 경로를 제외하고 url입력가능
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/ex1")
    public String ex1(Model model) {
        Point p = new Point(10, 20);
        model.addAttribute("data", p); // 앞에는 변수, 뒤에는 값

        return "thymeleaf/ex1";
    }

    @GetMapping("/ex2")
    public String ex2(Model model) {

        ItemFormDto itemDto = new ItemFormDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품 1");
        itemDto.setPrice(10000);

        model.addAttribute("itemDto", itemDto);

        return "thymeleaf/ex2";
    }

    @GetMapping({"/ex3", "/ex4"})
    public void ex3(Model model) {

        List<ItemFormDto> list = new ArrayList<>();

        for (int i=1; i<= 10; i++) {
            ItemFormDto itemDto = new ItemFormDto();
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품 1" + i);
            itemDto.setPrice(10000 * i);
            list.add(itemDto); // 추가
        }

        model.addAttribute("list", list);

    }

    @GetMapping("/ex5")
    public String ex5(@RequestParam("param1")String p1, String param2, Model model) { // 파라미터를 html 변수이름이랑 맞춰줘야함
        log.info("======>" + p1 + ", " + param2);

        model.addAttribute("param1", p1);
        model.addAttribute("param2", param2);

        return "thymeleaf/ex5";
    }

    @GetMapping({"/ex6", "/ex7"})
    public void ex6() {

    }

}
