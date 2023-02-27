package kr.inhatc.spring.test.controller;

import kr.inhatc.spring.test.dto.TestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {

        return "Hello world!!";
    }

    @GetMapping("/test")
    public TestDto test() {
        TestDto testDto = new TestDto();
        testDto.setName("홍길동");
        testDto.setAge(30);

        return testDto;
    }
}
