package com.example;

import com.example.Services.impl.UserManagerImpl;
import com.example.Utils.EmailUtils;
import com.example.Utils.PdfUtils;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Sigal on 5/16/2016.
 */
@Configuration
public class AppConfig {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations(
//                "classpath:/static/css/");
//        registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/static/bootstrap/");
//        registry.addResourceHandler("/scripts/**").addResourceLocations("classpath:/static/scripts/");
//    }

    @Bean
    public Utils getUtils() {
        return new Utils();
    }

    @Bean
    public UserManagerImpl getUserManager() {
        return new UserManagerImpl();
    }

    @Bean
    public TemplateUtils getTemplateUtils() {
        return new TemplateUtils();
    }

    @Bean
    public EmailUtils getEmailUtils() {
        return new EmailUtils();
    }

    @Bean
    public PdfUtils getPdfUtils() {
        return new PdfUtils();
    }


}
