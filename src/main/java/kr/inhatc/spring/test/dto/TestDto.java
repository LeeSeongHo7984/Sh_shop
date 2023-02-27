package kr.inhatc.spring.test.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor // 디폴트 생성자 어노테이션
@AllArgsConstructor 
@ToString // JPA 쓰다보면 ToString때문에 사고나서 조심해서 써야함
public class TestDto {

    private String name;
    private int age;
}
