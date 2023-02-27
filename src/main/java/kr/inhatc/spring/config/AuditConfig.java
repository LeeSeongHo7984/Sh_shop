package kr.inhatc.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 감시(변경 등록 등 감지)
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {

        return new AuditorAwareImpl();
    }
}
