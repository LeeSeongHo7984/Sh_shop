package kr.inhatc.spring.member.repository;

import kr.inhatc.spring.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // 꺾새 앞에는 entity, 뒤에는 id값 타입

    // Email 로 찾기
    Member findByEmail(String email); // Optional 은 Member 가 있을수도 있고 없을수도 있는 경우 일 때 사용함

}
