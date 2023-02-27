package kr.inhatc.spring.member.service;

import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // final을 쓸때 사용하는 어노테이션
@Log4j2
public class MemberService implements UserDetailsService {
    // ↑ UserDetailsService를 해야 자동로그인 가능(스프링 시큐리티 이용해서 로그인 가능)

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicate(member);

        return memberRepository.save(member);
    }

    private void validateDuplicate(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 등록된 사용자 입니다.");
        }
    }

    // security 관련 메소드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null) {  // .isPresent 는 있냐라고 물어보는 메서드
            throw new UsernameNotFoundException("해당 사용자가 없습니다. " + email);
        }

        log.info("=========> loadUserByUsername : " + member);

        return User.builder()  // builder를 넣으면 원하는 것만 찍어 넣을 수 있음 (3가지는 반드시 넣어야함), builder 를 쓰면 객체를 생성해줌
                .username(member.getEmail()) // 이메일
                .password(member.getPassword()) // 비밀번호
                .roles(member.getRole().toString()) // role
                .build();
    }
}
