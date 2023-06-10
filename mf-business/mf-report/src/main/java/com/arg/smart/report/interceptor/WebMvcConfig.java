package com.arg.smart.report.interceptor;

import cn.hutool.core.util.ArrayUtil;
import com.arg.smart.report.common.config.V2Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
 
	@Autowired
	private V2Config v2Config;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("error.html").addResourceLocations("classpath:/META-INF/resources/static/error.html");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        List<String> list1=new ArrayList<String>();
        List<String> list2=new ArrayList<String>();
        
        Map<String, String> map= v2Config.getXnljmap();
        
        Set<String> set = map.keySet();
        for (String o : set) {
            list1.add("/"+o+"/**");
            list2.add(map.get(o));
        }
    	registry.addResourceHandler(ArrayUtil.toArray(list1, String.class)).addResourceLocations(ArrayUtil.toArray(list2, String.class));
    }
    

    /**
     * 重写addCorsMappings()解决跨域问题
     * 配置：允许http请求进行跨域访问
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	
    	// 设置允许多个域名请求
        //String[] allowDomains = {"http://www.toheart.xin","http://192.168.11.213:8080","http://localhost:8080"};
    	
        //指哪些接口URL需要增加跨域设置
        registry.addMapping("/**")
                //.allowedOrigins("*")//指的是前端哪些域名被允许跨域
                .allowedOriginPatterns("*")
                //需要带cookie等凭证时，设置为true，就会把cookie的相关信息带上
                .allowCredentials(true)
                //指的是允许哪些方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //cookie的失效时间，单位为秒（s），若设置为-1，则关闭浏览器就失效
                .maxAge(3600);
    }
    
    /**
     * 重写addInterceptors()实现拦截器
     * 配置：要拦截的路径以及不拦截的路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器(Interceptor这个类是我们自己写的拦截器类)
        InterceptorRegistration registration = registry.addInterceptor(new Interceptor());
        //addPathPatterns()方法添加需要拦截的路径
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //excludePathPatterns()方法添加不拦截的路径
        
        
        String[] excludePatterns = new String[]{"/error","/error.html","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/api", "/api-docs", "/api-docs/**", "/doc.html/**",
                "/api/file/*"};
        
        //添加不拦截路径
        registration.excludePathPatterns(excludePatterns);
    }
 
}
