package com.sy.huangniao.controller.webMvc;

import com.sy.huangniao.controller.interceptor.LoginCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 登录验证拦截器
 * @author huchao
 *
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
	
	   @Autowired
	   private LoginCheck loginCheck;
	   /**
	    * 登录验证拦截器
	    */
	
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(loginCheck)
	        .addPathPatterns("/**")
	        .excludePathPatterns("/api/v1/user/login")
			.excludePathPatterns("/api/**/callback")
	        ;
	        super.addInterceptors(registry);
	    }
}
