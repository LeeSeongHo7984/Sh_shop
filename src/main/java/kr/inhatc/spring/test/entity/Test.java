package kr.inhatc.spring.test.entity;

import javax.persistence.*;

@Entity
public class Test {

    @Id // db 테이블의 기본키에 사용할 속성을 지정
    // 기본키 생성을 데이터베이스에 위임 ex)mysql db인 경우 AUTO_INCREMENT를 사용하여 기본키 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 자바규칙으로 변수명을 적으면 db에 알아서 my_name, my_age... 언더바를 생성해줌
    // 만약 언더바를 쓰고 싶지않다면 어노테이션 Column 을 써서 사용
    // 어노테이션 Column은 필드와 컬럼 매핑
    @Column(name = "name", nullable = false, length = 20)
    private String myName; // myName은 필드
    private int myAge; // myAge는 필드
    private String myInfo; // myInfo는 필드

}
