package kr.inhatc.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 시큐리티 설정
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()  // LoginForm default 가 나타남
            .loginPage("/member/login") // 로그인 페이지를 정함
            .defaultSuccessUrl("/") // 성공하면 root 로 이동
            .usernameParameter("email") //username 로 하면 쓸 필요가 없는데 만약에 id나 password, email 로 하면 직접 적어야함
            .failureUrl("/member/login/error") // 실패하면 error 로 보냄
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃이 될때 특정한 특정한 곳으로 이동
            .logoutSuccessUrl("/"); // logout 이 성공했을 때 나타나는 페이지

        http.authorizeRequests()
                .mvcMatchers("/css/**", "/js/**").permitAll() // **은 다 허용한다는 뜻
                .mvcMatchers("/", "/member/**", "/item/**").permitAll() // // **은 다 허용한다는 뜻
                .mvcMatchers("/admin/**").hasRole("ADMIN") // 권한이 ADMIN 인 애들만 ADMIN 밑에 메뉴에 접근 가능
                .anyRequest().authenticated();  // 일반적으로 치고 들어오는걸 막음, 인증을 받은 애들만 가능

        http.exceptionHandling()  // 권한이 없는데 치고들어오는 경우 처리
            .authenticationEntryPoint(new CustomEntryPoint());  // 권한을 주는 클래스 생성

        return http.build(); // 빈 애를 하나 만듬
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder(); // 단방향 암호화 객체 생성
    }
    
}


