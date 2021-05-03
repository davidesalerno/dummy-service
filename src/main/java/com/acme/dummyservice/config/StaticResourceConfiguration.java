package com.acme.dummyservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer
{
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
            {
                    "classpath:/META-INF/resources/", "classpath:/resources/",
                    "classpath:/static/", "classpath:/public/","classpath:/static/vendor/","classpath:/static/custom/"
            };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/ui/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ui").setViewName("forward:/ui/index.html");
//        registry.addViewController("/{spring:\\b(?!(?:ui)\\b)\\w+}")
//                .setViewName("forward:/");
//        registry.addViewController("/**/{spring:\\b(?!(?:ui)\\b)\\w+}")
//                .setViewName("forward:/");
//        registry.addViewController("/{spring:\\b(?!(?:ui)\\b)\\w+}/**{spring:?!(\\.js|\\.css)$}")
//                .setViewName("forward:/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(false);
    }
}
