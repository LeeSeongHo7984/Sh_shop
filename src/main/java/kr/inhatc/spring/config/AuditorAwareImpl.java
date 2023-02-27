package kr.inhatc.spring.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Log4j2
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication(); // 인증된 권한을 가져옴

        String userId = "";

        if(authentication != null) {
            userId = authentication.getName();
            log.info(">> 사용자 : " + userId);
        }

        return Optional.of(userId);
    }
}
