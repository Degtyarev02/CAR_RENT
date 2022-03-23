package com.example.CAR_RENT.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс для конфигурации доступа к статическим ресурсам
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Метод необходим для spring security, указывает название темплейта для логина
     *
     * @param registry вьюконтроллер регистрации
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("static/img/**")
                .addResourceLocations("classpath:/static/img/");
    }
}