package config;

import controllers.interceptors.UpdateStatusCheckingHandler;
import controllers.interceptors.UpdateStatusHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Web config
 **/
@Configuration
@EnableWebMvc
@ComponentScan("controllers")
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/CSS/**").addResourceLocations("/WEB-INF/CSS/");
        registry.addResourceHandler("/JS/**").addResourceLocations("/WEB-INF/JS/");
        registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/img/");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UpdateStatusCheckingHandler()).addPathPatterns("/table/*", "/csv/*");
        registry.addInterceptor(new UpdateStatusHandler()).addPathPatterns("/table/*");
        super.addInterceptors(registry);
    }
}
