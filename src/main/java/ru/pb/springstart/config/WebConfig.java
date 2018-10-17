package ru.pb.springstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pavel Barmyonkov on 10.10.18.
 * pbarmenkov@gmail.com
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.pb.springstart.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Bean
    public ViewResolver getViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setOrder(1);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");

        freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");

        return freeMarkerViewResolver;
    }

    @Bean
    public FreeMarkerConfigurer getFreeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths("/",
                "WEB-INF/views",
                "WEB-INF/views/office",
                "WEB-INF/views/layout",
                "WEB-INF/views/subdivision",
                "WEB-INF/views/employee"
        );
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");

        return freeMarkerConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        registry.addResourceHandler("/jquery/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.3.1-1/");

        registry.addResourceHandler("/bootstrap/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.1.1/");

        registry.addResourceHandler("/popper/**") //
                .addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.14.1/umd/");

    }


    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;

    }

}
