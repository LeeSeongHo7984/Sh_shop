package kr.inhatc.spring.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// MVC 처리 설정 관련
public class WebMvcConfig implements WebMvcConfigurer {

    @Value(value = "${uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // 이미지 등록 경로
                .addResourceLocations(uploadPath); // 실제 경로 (C 나 D 드라이브에 있는 경로)
    }


}
