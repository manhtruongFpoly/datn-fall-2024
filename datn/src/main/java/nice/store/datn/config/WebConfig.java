package nice.store.datn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đảm bảo Spring Boot phục vụ tệp từ thư mục ngoài
        registry.addResourceHandler("/desktop-images/**")
                .addResourceLocations("file:///" + System.getProperty("user.home") + "/Desktop/images/");
    }
}