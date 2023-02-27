package kr.inhatc.spring.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 항목 입니다.") // Null 인지 빈문자열인지 검사
    private String name;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식으로 입력하세요.") // 메일형식에 맞지 않으면 걸러줌
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목 입니다.")
    @Length(min = 4, max = 12, message = "최소 4자, 최대 12자를 입력하세요")
    private String password;

    @NotEmpty(message = "주소는 필수 항목 입니다.")
    private String address;

}
