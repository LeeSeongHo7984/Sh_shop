package kr.inhatc.spring.member.entity;

import kr.inhatc.spring.member.constant.Role;
import kr.inhatc.spring.member.dto.MemberFormDto;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 생성자
public class Member extends BaseEntity {

    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement 역활
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setRole(Role.USER); // Role은 dto에서 받아오지 않음
        String password = passwordEncoder.encode(memberFormDto.getPassword()); // 패스워드를 가져와서 암호화해서 넣어줌
        member.setPassword(password);

        return member;
    }

}
