package com.cutter.point.blog.picture;

import com.cutter.point.blog.picture.listener.ApplicationEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableTransactionManagement
//@SpringBootApplication(exclude = VelocityAutoConfiguration.class)
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
//@ComponentScan
public class PictureApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(PictureApplication.class);
		app.addListeners(new ApplicationEventListener());
		app.run(args);
	}
	
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
      
    /** 
     * 跨域过滤器 
     * @return 
     */
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }

}
