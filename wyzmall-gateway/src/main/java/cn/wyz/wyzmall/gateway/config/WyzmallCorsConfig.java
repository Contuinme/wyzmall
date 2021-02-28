package cn.wyz.wyzmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

@Configuration
public class WyzmallCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        ArrayList<String> origin = new ArrayList<>();
        origin.add("*");
        corsConfiguration.addAllowedHeader("*");                        //跨域请求头
        corsConfiguration.addAllowedMethod("*");                        //跨域请求方法
        corsConfiguration.setAllowedOriginPatterns(origin);             //跨域请求源
        corsConfiguration.setAllowCredentials(true);                    //跨域能否携带cookie
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);                               //跨域过滤器
    }
}
