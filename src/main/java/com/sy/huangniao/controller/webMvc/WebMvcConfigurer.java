package com.sy.huangniao.controller.webMvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 登录验证拦截器
 * @author huchao
 *
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {/*
	
	   @Autowired
	   private LoginCheck  loginCheck;
	   *//**
	    * 登录验证拦截器
	    *//*
	
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	    	
	        registry.addInterceptor(loginCheck)
	        .addPathPatterns("/**")
	        .excludePathPatterns("/login")
	        ;
	        super.addInterceptors(registry);
	    }
*/}
