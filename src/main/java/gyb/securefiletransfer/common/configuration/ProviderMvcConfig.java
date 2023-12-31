package gyb.securefiletransfer.common.configuration;

import gyb.securefiletransfer.common.handler.Intercepter.RateLimitingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Date 2023/10/13 23:06
 * @Author 郜宇博
 */
@Configuration
public class ProviderMvcConfig implements WebMvcConfigurer {
    @Autowired
    private RateLimitingInterceptor rateLimitingInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 项目中的所有接口都支持跨域
                .allowedOrigins("*") //允许哪些域能访问我们的跨域资源
                .allowedMethods("*")//允许的访问方法"POST", "GET", "PUT", "OPTIONS", "DELETE"等
                .allowedHeaders("*");//允许所有的请求header访问，可以自定义设置任意请求头信息
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitingInterceptor).addPathPatterns("/file/chunk");
    }
}
