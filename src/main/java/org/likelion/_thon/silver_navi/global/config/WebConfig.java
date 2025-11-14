package org.likelion._thon.silver_navi.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173", "http://localhost:5173/", // 프론트엔드 개발 서버 주소
                        "https://silvernavi.netlify.app",
                        "https://silvernavi-admin.netlify.app" // 프론트엔드 배포 서버 주소
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowCredentials(true);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // PageableHandlerMethodArgumentResolver를 커스텀합니다.
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        // 'page' 파라미터를 1-based(1부터 시작)로 인식하도록 설정합니다.
        pageableResolver.setOneIndexedParameters(true);

        resolvers.add(pageableResolver);
    }
}
