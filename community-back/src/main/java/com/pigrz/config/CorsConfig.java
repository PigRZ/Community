package com.pigrz.config;

import com.pigrz.pojo.Health;
import com.pigrz.util.FileUtil;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").
                allowedMethods("*"). //允许任何方法（post、get等）
                allowedHeaders("*"). //允许任何请求头
                allowCredentials(true);//带上cookie信息
//                exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(604800L);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("file:"+FileUtil.ROOT_PATH+"/static/","classpath:/static/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
    }

    class SessionInterceptor implements HandlerInterceptor {
        @Override
        public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
                throws Exception {
        }

        @Override
        public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
                throws Exception {
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
//            System.out.println("session" + request.getSession().getId());
            //首页路径以及登录放行
            if ("/userlogin".equals(request.getRequestURI()) || "/adminlogin".equals(request.getRequestURI()) || "/test".equals(request.getRequestURI())) {
                return true;
            } else {
                //重定向
                Object userid = request.getSession().getAttribute("userid");
                Object adminid = request.getSession().getAttribute("adminid");
                if ((adminid == null && request.getRequestURI().startsWith("/admin")) || (userid == null && request.getRequestURI().startsWith("/user"))) {
                    if(!request.getMethod().equals("OPTIONS")) {
                        System.out.println("非法请求,已拦截");
                    }
                    return false;
                } else {
                    if (request.getRequestURI().startsWith("/user")) {
                        System.out.println("用户"+userid + "请求" + request.getRequestURI());
                    } else if(request.getRequestURI().startsWith("/admin")) {
                        System.out.println("管理员"+adminid + "请求" + request.getRequestURI());
                    }
                    return true;
                }
            }
        }
    }
}