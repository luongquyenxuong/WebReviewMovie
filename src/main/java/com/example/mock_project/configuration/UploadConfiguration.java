package com.example.mock_project.configuration;

import com.example.mock_project.util.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UploadConfiguration implements WebMvcConfigurer {
    private static String UPLOAD_DIR_POST = FileUtils.getResourceBasePath() + "\\src\\main\\resources\\images\\post\\";
    private static String UPLOAD_DIR_AVATAR = FileUtils.getResourceBasePath() + "\\src\\main\\resources\\images\\avatar\\";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + UPLOAD_DIR_AVATAR, "file:" + UPLOAD_DIR_POST);
    }
}
